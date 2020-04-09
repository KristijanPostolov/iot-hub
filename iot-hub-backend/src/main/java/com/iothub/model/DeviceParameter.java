package com.iothub.model;

import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "device_parameter")
public class DeviceParameter {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  @Enumerated(value = EnumType.STRING)
  private ParameterType type;

  @Column(nullable = false)
  private Boolean active;

  @Column(nullable = false)
  private Boolean actuator;

  @Column(name = "start_timestamp")
  @Convert(converter = InstantConverter.class)
  private Instant startTimestamp;

  @Column(name = "end_timestamp")
  @Convert(converter = InstantConverter.class)
  private Instant endTimestamp;

  @Column(name = "last_value")
  private String lastValue;

  @ManyToOne
  @JoinColumn(name = "device_id")
  private Device device;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "deviceParameter")
  private List<ParameterValue> parameterValues;

}
