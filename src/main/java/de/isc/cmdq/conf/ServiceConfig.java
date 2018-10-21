package de.isc.cmdq.conf;

import org.springframework.context.annotation.*;

/**
 * Configuration for the core.
 * (c) ISC Clemenz &amp; Weinbrecht GmbH
 */
@Configuration
//@EnableLoadTimeWeaving
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(
        basePackages = {
         "de.isc.cmdq.service"
        }
)
@PropertySource("classpath:/cmdq.properties")
public class ServiceConfig {
  /**
   * Constructor.
   */
  public ServiceConfig() { /* empty */ }
}
