package com.iothub.spring;

import java.time.Instant;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.iothub.repository.JwtBlacklistRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class JwtBlacklistCleanupTask {

  private final JwtBlacklistRepository jwtBlacklistRepository;

  @Scheduled(cron = "${jwt.blacklist.cleanup.task.cron}")
  public void jwtBlacklistCleanup() {
    jwtBlacklistRepository.deleteAllByExpiryBefore(Instant.now());
  }

}
