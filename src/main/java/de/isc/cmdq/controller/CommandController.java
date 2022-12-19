package de.isc.cmdq.controller;

import de.isc.cmdq.domain.CmdRequest;
import de.isc.cmdq.service.Cmd;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Command execution via controller.
 *
 * &copy; ISC Clemenz &amp; Weinbrecht GmbH
 */
@RestController
@RequestMapping("/cmd")
@SuppressWarnings("unused")
public class CommandController {
  private final Logger LOG = LogManager.getLogger(CommandController.class);

  private final Cmd m_cmdSrv;

  /**
   * Constructor.
   *
   * @param cmdSrv the command service.
   */
  @Autowired
  public CommandController(final Cmd cmdSrv) {
    m_cmdSrv = cmdSrv;
  }

  /**
   * Initialized the controller.
   *
   * @return this
   */
  @PostConstruct
  CommandController init() {
    LOG.info("started command controller");
    return this;
  }

  /**
   *
   * @param cmdName add a command request
   * @return the id of the request
   */
  @PutMapping("/{cmd}")
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public String add(@PathVariable("cmd") final String cmdName) {
    LOG.debug("Command request: {}",cmdName);
    CmdRequest r = CmdRequest.builder()
                             .cmdName(cmdName)
                             .build();
    return m_cmdSrv.add(r);
  }
}
