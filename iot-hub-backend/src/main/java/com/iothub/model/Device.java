package com.iothub.model;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "device")
public class Device {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  private String description;

  @Column(name = "secret_key", unique = true)
  private String secretKey;

  @Column
  @Enumerated(value = EnumType.STRING)
  private DeviceStatus status;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "device", cascade = CascadeType.ALL)
  private List<DeviceParameter> deviceParameters;

}
