package de.isc.cmdq.service;

import de.isc.cmdq.domain.CmdQueueItem;
import de.isc.cmdq.domain.CmdRequest;
import de.isc.cmdq.error.ConfigError;
import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Command service.
 *
 * (c) ISC Clemenz &amp; Weinbrecht GmbH
 */
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
@SuppressWarnings("unused")
public class CmdImpl implements Cmd {
  private static final Logger LOG = LogManager.getLogger(CmdImpl.class);

  private ArrayBlockingQueue<CmdQueueItem> m_queue;
  private final Environment m_env;
  private final ApplicationContext m_app;
  private Thread m_worker;
  //---------------------------------------------------------------------------
  /**
   * Constructor.
   *
   * @param env the Spring-environment
   * @param app the Spring-Context
   */
  @Autowired
  public CmdImpl(final Environment env,
                 final ApplicationContext app) {
    m_env = env;
    m_app = app;
  }
  //---------------------------------------------------------------------------
  /**
   * Initialize the module.
   *
   * @return this
   */
  @PostConstruct
  Cmd init() {
    LOG.info("Initializing command queue");
    try {
      m_queue = new ArrayBlockingQueue<>(Integer.valueOf(m_env.getProperty("cmdq.queue.size", "200")));

      /*
       * This runnable executes the commands, here it's just
       * a 'sleep' of random length.
       */
      Runnable workHorse = () -> {
        try {
          while(true) {
            CmdQueueItem cmd = m_queue.take();
            LOG.debug("Now processing: {}",cmd.getId());
            // simulate some work...
            m_app.getBean(JythonService.class,"hello_param.py")
                 .execute(Map.of("user","T1000"));
          }
        } catch (InterruptedException x) {
          LOG.warn("Command queue processing interrupted");
        }
      };
      // launch the worker thread.
      m_worker = new Thread(workHorse);
      m_worker.setDaemon(true);
      m_worker.setName("Command-Queue-Worker");
      m_worker.start();
      LOG.info("Started command worker");


      return this;
    } catch(NumberFormatException | NullPointerException x) {
      throw new ConfigError("cmdq.queue.size");
    }
  }
  //---------------------------------------------------------------------------
  /**
   * Shutting service down.
   */
  @PreDestroy
  void done() {
    LOG.info("Stopping command queue");
    try {
      if(null != m_worker) {
        m_worker.interrupt();
      }

      m_queue.clear();
    } catch(Exception x) {
      LOG.error("Error (ignored) while stopping: " + x.getMessage());
    }
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
    /*
     * check if there is a worker.
     */
    if(m_worker.isInterrupted() || !m_worker.isAlive()) {
      throw new InternalError("Worker thread is stopped");
    }
    /*
     * add the command to the queue.
     */
    CmdQueueItem item = CmdQueueItem.from(req);

    try {
      m_queue.put(item);

      return item.getId().toString();
    } catch(InterruptedException x) {
      throw new InternalError(x);
    }
  }
  //---------------------------------------------------------------------------
  /**
   *
   * @return the current number of items in queue.
   */
  @Override
  public int size() {
    LOG.debug("size of queue requested");
    return m_queue.size();
  }
}
