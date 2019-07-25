package de.isc.cmdq.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;

/**
 * Basic data to describe an error.
 * &copy; ISC Clemenz &amp; Weinbrecht GmbH
 */
@Data
@Builder
@ToString
public class ErrorDescriptor {
  private String message;
  private String url;
  private int status;
  @Builder.Default private Instant time = Instant.now();
}
