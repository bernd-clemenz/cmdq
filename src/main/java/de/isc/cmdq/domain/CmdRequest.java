package de.isc.cmdq.domain;

import lombok.Data;

@Data
public class CmdRequest {
  private String cmdName;

  /**
   * Constructor.
   */
  public CmdRequest() { /* empty */}
}
