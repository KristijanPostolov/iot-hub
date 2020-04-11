package com.iothub.service.converter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iothub.exceptions.UnauthorizedException;
import com.iothub.model.Device;
import com.iothub.model.DeviceParameter;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthorizationService {

  public void authorize(Device device) {
     authorizeResource(device.getAccount().getEmail(), "DEVICE", device.getId());
  }

  public void authorize(DeviceParameter deviceParameter) {
    authorizeResource(deviceParameter.getDevice().getAccount().getEmail(), "DEVICE PARAMETER",
        deviceParameter.getId());
  }

  private void authorizeResource(String resourceOwner, String resourceType, Integer resourceId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!authentication.getName().equals(resourceOwner)) {
      log.warn("Unauthorized resource access. The logged user does not have the rights to access the "
          + resourceType + " with id: " + resourceId);
      throw new UnauthorizedException("The logged user does not have the rights to access the resource");
    }
  }

}
