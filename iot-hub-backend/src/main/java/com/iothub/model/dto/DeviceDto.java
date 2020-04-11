package com.iothub.model.dto;

import java.util.List;

import com.iothub.model.DeviceStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DeviceDto {

  private Integer id;
  private String name;
  private String description;
  private String secretKey;
  private DeviceStatus status;

  private List<DeviceParameterDto> parameters;
  private List<DeviceParameterDto> oldParameters;
}
