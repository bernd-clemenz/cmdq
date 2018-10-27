package de.isc.cmdq.domain;

import lombok.Builder;
import lombok.Data;

/**
 * Command request descriptor.
 */
@Data
@Builder
public class CmdRequest {
  private String cmdName;
}
