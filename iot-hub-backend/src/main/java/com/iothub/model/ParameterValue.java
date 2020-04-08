package com.iothub.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;

@Getter
@Entity
@Table(name = "parameter_value")
public class ParameterValue {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  @Convert(converter = InstantConverter.class)
  private Instant timestamp;

  private String value;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "device_parameter_id")
  private DeviceParameter deviceParameter;

}
