package com.iothub.service.messaging;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iothub.messages.ConfigMessage;
import com.iothub.messages.StateMessage;
import com.iothub.service.DeviceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceMessageListener {

  private final ObjectMapper objectMapper;
  private final DeviceService deviceService;

  public void handleConfigMessage(String topic, byte[] payload) {
    String deviceId = extractDeviceId(topic);
    try {
      ConfigMessage message = objectMapper.readValue(payload, ConfigMessage.class);
      boolean completed = deviceService.updateDeviceConfiguration(deviceId, message);
      if (!completed) {
        log.warn("Failed updating device configuration");
      }
    } catch (IOException e) {
     log.warn("Invalid config message received from device: " + deviceId, e);
    }
  }

  public void handleStateMessage(String topic, byte[] payload) {
    String deviceId = extractDeviceId(topic);
    try {
      StateMessage message = objectMapper.readValue(payload, StateMessage.class);
      boolean completed = deviceService.updateDeviceState(deviceId, message);
      if (!completed) {
        log.warn("Failed updating device state");
      }
    } catch (IOException e) {
      log.warn("Invalid config message received from device: " + deviceId, e);
    }
  }

  private String extractDeviceId(String topic) {
    return topic.split("/")[1];
  }
}
