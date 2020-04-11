package com.iothub.model.dto;

import java.time.Instant;
import java.util.Map;

import com.iothub.model.ParameterType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DeviceParameterDto {
  private int id;
  private String name;
  private ParameterType type;
  private boolean active;
  private boolean actuator;
  private String lastValue;
  private Instant lastUpdate;

  private Map<Instant, String> values;
}
