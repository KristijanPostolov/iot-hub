package com.iothub.simulator.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iothub.messages.StatePushMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class PushStateListener {

  private final ObjectMapper objectMapper;
  private final DeviceState deviceState;

  public void handlePushStateMessage(String topic, byte[] payload) {
    String key = topic.split("/")[1];
    if (!key.equals(deviceState.getKey())) {
      log.error("Fatal error, we received message for wrong device(topic)");
      return;
    }

    try {
      log.info("We have received push state message");
      StatePushMessage message = objectMapper.readValue(new String(payload), StatePushMessage.class);
      message.getState().forEach((name, value) -> {
        if (!deviceState.getTypes().containsKey(name)) {
          log.warn("We received push state for an invalid parameter");
        }
        deviceState.getValues().put(name, value);
        log.info("Changed value: " + name + " -> " + value);
      });
    } catch (IOException e) {
      log.warn("We received an invalid push state message");
    }
  }

}
