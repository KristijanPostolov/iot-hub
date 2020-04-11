package com.iothub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iothub.model.DeviceParameter;

@Repository
public interface DeviceParameterRepository extends JpaRepository<DeviceParameter, Integer> {

  DeviceParameter findById(int id);

}
