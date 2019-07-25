package de.isc.cmdq.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Collecting some statistics as an Aspect.
 *
 * &copy; ISC Clemenz &amp; Weinbrecht GmbH
 */
@Aspect
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
@SuppressWarnings("unused")
public class StatisticsCollector {
  private static final Logger LOG = LogManager.getLogger(StatisticsCollector.class);

  private AtomicLong m_addQueueCalls = new AtomicLong(0L);
  private AtomicLong m_scriptCalls = new AtomicLong(0L);
  /**
   * Constructor.
   */
  public StatisticsCollector() {
    // empty
  }

  @Around("execution(* de.isc.cmdq.service.CmdImpl.add(..))")
  public Object aroundQueueAdd(final ProceedingJoinPoint jp)
  throws Throwable {
    LOG.debug("Around add command");
    m_addQueueCalls.incrementAndGet();

    return jp.proceed();
  }

  @Before("execution(* de.isc.cmdq.service.JythonService.execute(..))")
  public void beforeScriptExecute(final JoinPoint jp) {
    LOG.debug("Before: {}",jp.getSignature().getName());
    m_scriptCalls.incrementAndGet();
  }

  /**
   * @return recorded add-queue calls
   */
  public long countQueueAdd() { return m_addQueueCalls.get(); }

  /**
   * @return recorded script execution calls
   */
  public long countScriptCalls() { return m_scriptCalls.get(); }
}
