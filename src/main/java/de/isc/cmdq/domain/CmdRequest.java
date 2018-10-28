package de.isc.cmdq.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Command request descriptor.
 */
@Data
@Builder
public class CmdRequest {
  @NonNull private String cmdName;
}
