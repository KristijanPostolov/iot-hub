package com.iothub.repository;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iothub.model.JwtBlacklist;

public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklist, String> {

  boolean existsById(String id);
  void deleteAllByExpiryBefore(Instant limit);

}
