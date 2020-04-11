package com.iothub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iothub.model.dto.DeviceParameterDto;
import com.iothub.service.DeviceParameterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/parameters")
@RequiredArgsConstructor
public class DeviceParameterController {

  private final DeviceParameterService deviceParameterService;

  @GetMapping("/{id}")
  public DeviceParameterDto getDeviceParameterDetails(@PathVariable int id) {
    return deviceParameterService.getDeviceParameterDetails(id);
  }

}
