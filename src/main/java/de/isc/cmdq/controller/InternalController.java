package de.isc.cmdq.controller;

import de.isc.cmdq.error.InternalResourceNotFound;
import de.isc.cmdq.service.InternalDataService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Controller to publish application state date.
 * (c) ISC Clemenz &amp; Weinbrecht GmbH
 */
@RestController
@RequestMapping("/internal")
@SuppressWarnings("unused")
public class InternalController {
  private static final Logger LOG = LogManager.getLogger(InternalController.class);

  private final InternalDataService m_service;
  //---------------------------------------------------------------------------
  /**
   * Constructor.
   *
   * @param srv the internal data service.
   */
  @Autowired
  public InternalController(final InternalDataService srv) {
    m_service = srv;
  }
  //---------------------------------------------------------------------------
  /**
   * @return this
   */
  InternalController init() {
    LOG.info("Internal controller starting.");
    return this;
  }
  //---------------------------------------------------------------------------
  /**
   * @return build data
   * @throws InternalError i case of a read issue
   * @throws InternalResourceNotFound in case of NPE
   */
  @GetMapping("/version")
  @ResponseBody
  public Map<?,?> version() {
    LOG.debug("version requested");
    Properties version = new Properties();
    try(InputStream ins = InternalController.class.getResourceAsStream("/version.properties")) {
      version.load(ins);
    } catch(IOException x) {
      throw new InternalError(x);
    } catch(NullPointerException x) {
      throw new InternalResourceNotFound(x);
    }

    return version;
  }
  //---------------------------------------------------------------------------
  /**
   * @return internal status data
   */
  @GetMapping("/status")
  @ResponseBody
  public Map<?,?> statusData() {
    LOG.debug("status requested");
    // Double-Bracket initialization
    return Collections.unmodifiableMap(new HashMap<String, Object>() {{
      put("memory",m_service.memory());
      put("start.time",m_service.startTime());
      put("add.queue.calls",m_service.countAddQueueCall());
    }});
  }
}
