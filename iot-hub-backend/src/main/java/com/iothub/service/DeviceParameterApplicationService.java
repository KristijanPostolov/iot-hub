package com.iothub.service;

import org.springframework.stereotype.Service;

import com.iothub.model.dto.DeviceParameterDto;
import com.iothub.model.dto.UpdateValueRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceParameterApplicationService {

  private final DeviceParameterService deviceParameterService;

  public DeviceParameterDto getDeviceParameterDetails(int id) {
    return deviceParameterService.getDeviceParameterDetails(id);
  }

  public void updateParameterValue(int id, UpdateValueRequest updateValueRequest) {
    deviceParameterService.updateParameterValue(id, updateValueRequest.getNewValue());
    // todo: update device state through message queue
  }

}
