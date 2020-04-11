package com.iothub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iothub.model.dto.DeviceParameterDto;
import com.iothub.model.dto.UpdateValueRequest;
import com.iothub.service.DeviceParameterApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/parameters")
@RequiredArgsConstructor
public class DeviceParameterController {

  private final DeviceParameterApplicationService deviceParameterApplicationService;

  @GetMapping("/{id}")
  public DeviceParameterDto getDeviceParameterDetails(@PathVariable int id) {
    return deviceParameterApplicationService.getDeviceParameterDetails(id);
  }

  @PostMapping("/{id}")
  public void updateParameterValue(@PathVariable int id, @RequestBody UpdateValueRequest updateValueRequest) {
    deviceParameterApplicationService.updateParameterValue(id, updateValueRequest);
  }

}
