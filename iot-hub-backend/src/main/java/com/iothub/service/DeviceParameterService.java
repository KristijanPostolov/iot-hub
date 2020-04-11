package com.iothub.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.iothub.exceptions.MissingResourceException;
import com.iothub.model.DeviceParameter;
import com.iothub.model.dto.DeviceParameterDto;
import com.iothub.repository.DeviceParameterRepository;
import com.iothub.service.converter.AuthorizationService;
import com.iothub.service.converter.DeviceParameterConverter;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class DeviceParameterService {

  private final DeviceParameterRepository deviceParameterRepository;
  private final AuthorizationService authorizationService;
  private final DeviceParameterConverter deviceParameterConverter;

  public DeviceParameterDto getDeviceParameterDetails(int id) {
    DeviceParameter deviceParameter = deviceParameterRepository.findById(id);
    if (deviceParameter == null) {
      throw new MissingResourceException("A device parameter with id " + id + " does not exist");
    }
    authorizationService.authorize(deviceParameter);
    return deviceParameterConverter.convertToDeviceParameterDetails(deviceParameter);
  }

  public DeviceParameter updateParameterValue(int id, String newValue) {
    DeviceParameter deviceParameter = deviceParameterRepository.findById(id);
    if (deviceParameter == null) {
      throw new MissingResourceException("A device parameter with id " + id + " does not exist");
    }
    authorizationService.authorize(deviceParameter);
    deviceParameter.setLastValue(newValue);
    deviceParameter.setLastUpdate(Instant.now());
    deviceParameterRepository.save(deviceParameter);
    return deviceParameter;
  }

}
