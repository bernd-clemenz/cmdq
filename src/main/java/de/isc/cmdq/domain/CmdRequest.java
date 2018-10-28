package de.isc.cmdq.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Command request descriptor.
 * (c) ISC Clemenz &amp; Weinbrecht GmbH
 */
@Data
@Builder
public class CmdRequest {
  @NonNull private String cmdName;
}
