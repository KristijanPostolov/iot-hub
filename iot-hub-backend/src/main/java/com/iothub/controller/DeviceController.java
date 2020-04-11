package com.iothub.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iothub.model.dto.DeviceDto;
import com.iothub.service.DeviceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

  private final DeviceService deviceService;

  @GetMapping
  public List<DeviceDto> getDevicesForLoggedUser(Principal principal) {
    return deviceService.getLoggedUserDevices(principal);
  }

  @GetMapping("/{id}")
  public DeviceDto getDevice(@PathVariable int id) {
    return deviceService.getDevice(id);
  }

  @PostMapping
  public DeviceDto createDevice(@RequestBody DeviceDto deviceDto, Principal principal) {
    return deviceService.createDevice(deviceDto, principal);
  }
}
