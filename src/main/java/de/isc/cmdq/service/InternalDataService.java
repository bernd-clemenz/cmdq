package de.isc.cmdq.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
  /**
   * Constructor.
   */
  public InternalDataService() { /* empty */ }

  public long memory() { return Runtime.getRuntime().freeMemory(); }
}
