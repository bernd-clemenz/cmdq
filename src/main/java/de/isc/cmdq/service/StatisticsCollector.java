package de.isc.cmdq.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Aspect
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class StatisticsCollector {
  private static final Logger LOG = LogManager.getLogger(StatisticsCollector.class);

  private AtomicLong m_addQueueCalls = new AtomicLong(0L);
  /**
   * Constructor.
   */
  public StatisticsCollector() {
    // empty
  }

  @Around("execution(* de.isc.cmdq.service.CmdImpl.add(..))")
  public Object aroundQueueAdd(final ProceedingJoinPoint jp)
  throws Throwable {
    LOG.debug("AROUND");
    m_addQueueCalls.incrementAndGet();

    return jp.proceed();
  }

  public long countQueueAdd() { return m_addQueueCalls.get(); }
}
