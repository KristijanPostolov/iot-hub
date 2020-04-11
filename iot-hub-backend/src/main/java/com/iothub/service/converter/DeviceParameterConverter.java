package com.iothub.service.converter;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.iothub.model.DeviceParameter;
import com.iothub.model.dto.DeviceParameterDto;

@Service
public class DeviceParameterConverter {

  public List<DeviceParameterDto> convertToDto(List<DeviceParameter> deviceParameters) {
    return deviceParameters.stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
  }

  public DeviceParameterDto convertToDto(DeviceParameter deviceParameter) {
    return fillCommonFields(deviceParameter).build();
  }

  public DeviceParameterDto convertToDeviceParameterDetails(DeviceParameter deviceParameter) {
    Map<Instant, String> values = new TreeMap<>();
    deviceParameter.getParameterValues()
        .forEach(parameterValue -> values.put(parameterValue.getTimestamp(), parameterValue.getValue()));
    return fillCommonFields(deviceParameter)
        .values(values)
        .build();
  }

  private DeviceParameterDto.DeviceParameterDtoBuilder fillCommonFields(DeviceParameter deviceParameter) {
    return DeviceParameterDto.builder()
        .id(deviceParameter.getId())
        .name(deviceParameter.getName())
        .active(deviceParameter.getActive())
        .actuator(deviceParameter.getActuator())
        .lastValue(deviceParameter.getLastValue())
        .lastUpdate(deviceParameter.getLastUpdate())
        .type(deviceParameter.getType());
  }
}
