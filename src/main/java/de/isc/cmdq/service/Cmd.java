package de.isc.cmdq.service;

import de.isc.cmdq.domain.CmdRequest;

/**
 * Operations of the command queue.
 * (c) ISC Clemenz &amp; Weinbrecht GmbH
 */
public interface Cmd {
  /**
   * Add a command request to the queue.
   *
   * @param req the command request
   * @return the ID of the inserted command request (for further reference)
   */
  String add(CmdRequest req);

  /**
   * @return number of items currently in queue
   */
  int size();
}
