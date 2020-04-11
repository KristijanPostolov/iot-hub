package com.iothub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iothub.model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

  List<Device> findByAccount_Email(String email);
  Device findById(int id);

}
