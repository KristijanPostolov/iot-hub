package com.iothub.service.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.iothub.model.Device;
import com.iothub.model.dto.DeviceDto;
import com.iothub.model.dto.DeviceParameterDto;

@Service
public class DeviceConverter {

  public List<DeviceDto> convertToDto(List<Device> devices) {
    return devices.stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
  }

  public DeviceDto convertToDto(Device device) {
    return DeviceDto.builder()
        .id(device.getId())
        .name(device.getName())
        .description(device.getDescription())
        .status(device.getStatus())
        .secretKey(device.getSecretKey())
        .build();
  }

  public DeviceDto convertToDeviceDetails(Device device, List<DeviceParameterDto> deviceParameters) {
    List<DeviceParameterDto> activeParameters = deviceParameters.stream()
        .filter(DeviceParameterDto::isActive)
        .collect(Collectors.toList());

    List<DeviceParameterDto> oldParameters = deviceParameters.stream()
        .filter(deviceParameterDto -> !deviceParameterDto.isActive())
        .collect(Collectors.toList());

    return DeviceDto.builder()
        .id(device.getId())
        .name(device.getName())
        .description(device.getDescription())
        .status(device.getStatus())
        .secretKey(device.getSecretKey())
        .parameters(activeParameters)
        .oldParameters(oldParameters)
        .build();
  }

}
