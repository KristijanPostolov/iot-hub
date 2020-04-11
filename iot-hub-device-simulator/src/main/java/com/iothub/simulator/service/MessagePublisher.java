package com.iothub.simulator.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iothub.messages.ConfigMessage;
import com.iothub.messages.StateMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessagePublisher {

  private final MqttClient mqttClient;
  private final DeviceState deviceState;
  private final ObjectMapper objectMapper;

  public void sendStateMessage(StateMessage stateMessage) {
    try {
      String jsonMessage = objectMapper.writeValueAsString(stateMessage);
      mqttClient.publish("device/" + deviceState.getKey() + "/state", jsonMessage.getBytes(), 1, true);
    } catch (JsonProcessingException e) {
      log.error("Error serializing state message");
    } catch (MqttException e) {
      log.error("Error sending state message");
    }
  }

  public void sendConfigMessage(ConfigMessage configMessage) {
    try {
      String jsonMessage = objectMapper.writeValueAsString(configMessage);
      mqttClient.publish("device/" + deviceState.getKey() + "/config", jsonMessage.getBytes(), 1, true);
    } catch (JsonProcessingException e) {
      log.error("Error serializing config message");
    } catch (MqttException e) {
      log.error("Error sending config message");
    }
  }

}
