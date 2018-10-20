package de.isc.cmdq.service;

import de.isc.cmdq.domain.CmdQueueItem;
import de.isc.cmdq.domain.CmdRequest;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
@SuppressWarnings("unused")
public class CmdImpl implements Cmd {
  private static final Logger LOG = LogManager.getLogger(CmdImpl.class);

  private Queue<CmdQueueItem> m_queue;
  private final Environment m_env;
  //---------------------------------------------------------------------------
  /**
   * Constructor.
   * @param env the spring-environment
   */
  @Autowired
  public CmdImpl(final Environment env) {
    m_env = env;
  }
  //---------------------------------------------------------------------------
  /**
   * Initialize the module.
   *
   * @return this
   */
  @PostConstruct
  Cmd init() {
    LOG.debug("Initializing command queue");
    m_queue = new ArrayBlockingQueue<>(Integer.valueOf(m_env.getProperty("cmdq.queue.size","200")));

    return this;
  }
  //---------------------------------------------------------------------------
  /**
   * Adds an item at the end of the queue
   * @param req the command request
   * @return id of the corresponding request item
   */
  @Override
  public String add(final CmdRequest req) {
    LOG.debug("Adding a command-request");
    CmdQueueItem item = CmdQueueItem.from(req);

    m_queue.add(item);

    return item.getId().toString();
  }
}
