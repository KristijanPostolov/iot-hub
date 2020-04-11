package com.iothub.simulator.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mqtt")
public class MqttConnectionProperties {
  private String connectionUrl;
  private Boolean automaticReconnect;
  private Integer connectionTimeout;
}
