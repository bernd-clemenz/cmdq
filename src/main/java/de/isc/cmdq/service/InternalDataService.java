package de.isc.cmdq.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;

/**
 * Service collecting and providing application internal
 * state data.
 * (c) ISC Clemenz &amp; Weinbrecht GmbH
 *
 */
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class InternalDataService {
  private static final Logger LOG = LogManager.getLogger(InternalDataService.class);

  private final Instant m_startTime = Instant.now();
  /**
   * Constructor.
   */
  public InternalDataService() { /* empty */ }

  @PostConstruct
  InternalDataService init() {
    LOG.debug("Init internal data service");
    return this;
  }

  /**
   * @return free memory in bytes
   */
  public long memory() { return Runtime.getRuntime().freeMemory(); }

  /**
   * @return start time of the service
   */
  public Instant startTime() { return m_startTime; }
}
