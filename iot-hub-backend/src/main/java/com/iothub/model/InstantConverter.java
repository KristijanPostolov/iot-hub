package com.iothub.model;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.persistence.AttributeConverter;

public class InstantConverter implements AttributeConverter<Instant, Timestamp> {
  @Override
  public Timestamp convertToDatabaseColumn(Instant instant) {
    return instant == null ? null : Timestamp.valueOf(LocalDateTime.ofInstant(instant, ZoneOffset.UTC));
  }

  @Override
  public Instant convertToEntityAttribute(Timestamp sqlTimestamp) {
    return sqlTimestamp == null ? null : sqlTimestamp.toInstant();
  }
}
