package com.iothub.simulator.configuration;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttSimulatorConfiguration {

  private final MqttConnectionProperties connectionProperties;

  public MqttSimulatorConfiguration(MqttConnectionProperties connectionProperties) {
    this.connectionProperties = connectionProperties;
  }

  @Bean
  public MqttClient mqttClient() throws MqttException {
    String clientId = UUID.randomUUID().toString();
    MqttClient mqttClient = new MqttClient(connectionProperties.getConnectionUrl(), clientId);

    MqttConnectOptions options = new MqttConnectOptions();
    options.setAutomaticReconnect(connectionProperties.getAutomaticReconnect());
    options.setConnectionTimeout(connectionProperties.getConnectionTimeout());
    mqttClient.connect(options);
    return mqttClient;
  }

}
