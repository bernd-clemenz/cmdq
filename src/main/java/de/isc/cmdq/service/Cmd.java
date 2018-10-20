package de.isc.cmdq.service;

import de.isc.cmdq.domain.CmdRequest;

/**
 * Operations of the command queue.
 */
public interface Cmd {
  /**
   * Add a command request to the queue.
   *
   * @param req the command request
   * @return the ID of the inserted command request (for further reference)
   */
  String add(CmdRequest req);
}
