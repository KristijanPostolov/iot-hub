package com.iothub.service.messaging;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iothub.exceptions.InternalServerException;
import com.iothub.messages.StatePushMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceMessagePublisher {

  private final MqttClient mqttClient;
  private final ObjectMapper objectMapper;

  public void pushStatus(String key, StatePushMessage statePushMessage) {
    try {
      String jsonMessage = objectMapper.writeValueAsString(statePushMessage);
      mqttClient.publish("devices/" + key + "/push", jsonMessage.getBytes(), 1, true);
    } catch (JsonProcessingException e) {
      log.error("State push message serialization error");
      throw new InternalServerException("Could not finish operation due to internal server error");
    } catch (MqttException e) {
      log.error("State push message sending error");
      throw new InternalServerException("Could not finish operation due to internal server error");
    }
  }

}
