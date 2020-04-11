package com.iothub.spring;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.iothub.service.messaging.DeviceMessageListener;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MqttConfiguration {

  private final MqttConfigurationProperties configurationProperties;
  private final DeviceMessageListener deviceMessageListener;

  @Bean
  public MqttClient mqttClient() throws MqttException {
    String clientId = UUID.randomUUID().toString();
    MqttClient mqttClient = new MqttClient(configurationProperties.getConnectionUrl(), clientId);

    MqttConnectOptions options = new MqttConnectOptions();
    options.setAutomaticReconnect(configurationProperties.getAutomaticReconnect());
    options.setConnectionTimeout(configurationProperties.getConnectionTimeout());
    mqttClient.connect(options);
    mqttClient.subscribe("devices/+/config", (topic, mqttMessage) -> {
      deviceMessageListener.handleConfigMessage(topic, mqttMessage.getPayload());
    });
    mqttClient.subscribe("devices/+/state", (topic, mqttMessage) -> {
      deviceMessageListener.handleStateMessage(topic, mqttMessage.getPayload());
    });
    return mqttClient;
  }



}
