package com.iothub.simulator.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * Stateful service that stores device information.
 */
@Component
@Setter
@Getter
public class DeviceState {

  private String key;
  private Map<String, String> types;
  private Map<String, String> values;

}
