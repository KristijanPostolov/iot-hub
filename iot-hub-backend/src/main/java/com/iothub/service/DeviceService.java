package com.iothub.service;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.iothub.exceptions.MissingResourceException;
import com.iothub.model.Account;
import com.iothub.model.Device;
import com.iothub.model.DeviceStatus;
import com.iothub.model.dto.DeviceDto;
import com.iothub.model.dto.DeviceParameterDto;
import com.iothub.repository.DeviceRepository;
import com.iothub.service.converter.AuthorizationService;
import com.iothub.service.converter.DeviceConverter;
import com.iothub.service.converter.DeviceParameterConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
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
}
