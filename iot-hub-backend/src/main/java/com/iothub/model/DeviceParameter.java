package com.iothub.model;

import java.time.Instant;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

  @Column(name = "last_update")
  @Convert(converter = InstantConverter.class)
  private Instant lastUpdate;

  @ManyToOne
  @JoinColumn(name = "device_id")
  private Device device;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "deviceParameter", cascade = CascadeType.ALL)
  private List<ParameterValue> parameterValues;

}
