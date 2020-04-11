package com.iothub.service;

import java.security.Principal;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.iothub.exceptions.MissingResourceException;
import com.iothub.messages.ConfigMessage;
import com.iothub.messages.ParameterConfig;
import com.iothub.messages.StateMessage;
import com.iothub.model.Account;
import com.iothub.model.Device;
import com.iothub.model.DeviceParameter;
import com.iothub.model.DeviceStatus;
import com.iothub.model.ParameterType;
import com.iothub.model.ParameterValue;
import com.iothub.model.dto.DeviceDto;
import com.iothub.model.dto.DeviceParameterDto;
import com.iothub.repository.DeviceRepository;
import com.iothub.service.converter.AuthorizationService;
import com.iothub.service.converter.DeviceConverter;
import com.iothub.service.converter.DeviceParameterConverter;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class DeviceService {

  private final DeviceRepository deviceRepository;
  private final DeviceConverter deviceConverter;
  private final DeviceParameterConverter deviceParameterConverter;
  private final AccountService accountService;
  private final AuthorizationService authorizationService;

  public List<DeviceDto> getLoggedUserDevices(Principal principal) {
    String email = principal.getName();
    List<Device> devices = deviceRepository.findByAccount_Email(email);
    return deviceConverter.convertToDto(devices);
  }

  public DeviceDto getDevice(int id) {
    Device device = deviceRepository.findById(id);
    if (device == null) {
      throw new MissingResourceException("A device with id " + id + " does not exist.");
    }
    authorizationService.authorize(device);
    List<DeviceParameterDto> deviceParameters = deviceParameterConverter.convertToDto(device.getDeviceParameters());
    return deviceConverter.convertToDeviceDetails(device, deviceParameters);
  }

  public DeviceDto createDevice(DeviceDto deviceDto, Principal principal) {
    Account account = accountService.findByEmail(principal.getName());
    String secretKey = UUID.randomUUID().toString();
    Device device = Device.builder()
        .name(deviceDto.getName())
        .description(deviceDto.getDescription())
        .status(DeviceStatus.INACTIVE)
        .secretKey(secretKey)
        .account(account)
        .build();

    device = deviceRepository.save(device);
    return deviceConverter.convertToDto(device);
  }

  public boolean updateDeviceConfiguration(String id, ConfigMessage configMessage) {
    Device device = deviceRepository.findBySecretKey(id);
    if (device == null) {
      return false;
    }
    if (DeviceStatus.INACTIVE.equals(device.getStatus())) {
      device.setStatus(DeviceStatus.PAIRED);
    }
    Instant now = Instant.now();
    List<DeviceParameter> deviceParameters = device.getDeviceParameters();
    Map<String, ParameterConfig> newConfig = configMessage.getConfiguration();

    Set<String> existing = new HashSet<>();
    // check existing parameters
    for(DeviceParameter parameter : deviceParameters) {
      ParameterConfig newParameter = newConfig.get(parameter.getName());
      existing.add(parameter.getName());
      if (newParameter == null) {
        // removed
        parameter.setActive(false);
        parameter.setEndTimestamp(now);
      } else {
        // existing (validate that type is not changed)
        if (!parameter.getType().name().equals(newParameter.getType().name())) {
          return false;
        }
        parameter.setActuator(newParameter.isActuator());
      }
    }
    // add new parameters
    newConfig.forEach((name, config) -> {
      if (!existing.contains(name)) {
        DeviceParameter newParameter = DeviceParameter.builder()
            .name(name)
            .type(ParameterType.valueOf(config.getType().name()))
            .active(true)
            .actuator(config.isActuator())
            .startTimestamp(now)
            .device(device)
            .build();
        deviceParameters.add(newParameter);
      }
    });
    device.setDeviceParameters(deviceParameters);
    deviceRepository.save(device);
    return true;
  }

  public boolean updateDeviceState(String id, StateMessage stateMessage) {
    Device device = deviceRepository.findBySecretKey(id);
    if (device == null || device.getStatus() == null || DeviceStatus.INACTIVE.equals(device.getStatus())) {
      return false;
    }
    if (DeviceStatus.PAIRED.equals(device.getStatus())) {
      device.setStatus(DeviceStatus.ACTIVE);
    }

    Instant timestamp = stateMessage.getTimestamp();
    Map<String, String> state = stateMessage.getState();
    int updated = 0;
    for (DeviceParameter parameter : device.getDeviceParameters()) {
      if (!parameter.getActive()) {
        return false;
      }
      String newValue = state.get(parameter.getName());
      if (newValue != null) {
        ParameterValue parameterValue = ParameterValue.builder()
            .value(newValue)
            .timestamp(timestamp)
            .deviceParameter(parameter)
            .build();
        parameter.getParameterValues().add(parameterValue);
        parameter.setLastValue(newValue);
        parameter.setLastUpdate(timestamp);
        updated++;
      }
    }
    if (updated < state.size()) {
      return false;
    }
    deviceRepository.save(device);
    return true;
  }
}
