package com.iothub.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;

@Getter
@Entity
@Table(name = "device")
public class Device {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  @Column(name = "secret_key", unique = true)
  private String secretKey;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "device")
  private List<DeviceParameter> deviceParameters;

}
