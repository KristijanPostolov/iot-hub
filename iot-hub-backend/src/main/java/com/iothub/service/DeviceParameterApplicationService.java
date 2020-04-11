package com.iothub.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.iothub.messages.StatePushMessage;
import com.iothub.model.DeviceParameter;
import com.iothub.model.dto.DeviceParameterDto;
import com.iothub.model.dto.UpdateValueRequest;
import com.iothub.service.messaging.DeviceMessagePublisher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceParameterApplicationService {

  private final DeviceParameterService deviceParameterService;
  private final DeviceMessagePublisher deviceMessagePublisher;

  public DeviceParameterDto getDeviceParameterDetails(int id) {
    return deviceParameterService.getDeviceParameterDetails(id);
  }

  public void updateParameterValue(int id, UpdateValueRequest updateValueRequest) {
    DeviceParameter deviceParameter = deviceParameterService.updateParameterValue(id, updateValueRequest.getNewValue());
    // uuid can be used to keep track of push message status (acknowledging state change)
    String uuid = UUID.randomUUID().toString();
    Map<String, String> updates = new HashMap<>();
    updates.put(deviceParameter.getName(), updateValueRequest.getNewValue());
    StatePushMessage statePushMessage = new StatePushMessage(uuid, updates);
    deviceMessagePublisher.pushStatus(deviceParameter.getDevice().getSecretKey(), statePushMessage);
  }

}
