package de.isc.cmdq.service;

import de.isc.cmdq.domain.CmdQueueItem;
import de.isc.cmdq.error.InternalResourceNotFound;
import de.isc.cmdq.error.ScriptError;
import jakarta.annotation.PostConstruct;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.python.core.PyException;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

/**
 * Jython script execution service.
 *
 * &copy; ISC Clemenz &amp; Weinbrecht GmbH
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@SuppressWarnings("unused")
public class JythonService {
  private static final Logger LOG = LogManager.getLogger(JythonService.class);
  static {
    // avoid loading python libraries ...
    System.setProperty("python.import.site","false");
  }

  private final String m_scriptName;
  private final CmdQueueItem m_request;
  private final PythonInterpreter m_python = new PythonInterpreter();
  private String m_script;

  /**
   * Constructor.
   *
   * @param scriptName the name of the script to execute
   * @param request the request for that script execution.
   */
  public JythonService(final String scriptName,
                       final CmdQueueItem request) {
    m_scriptName = scriptName;
    m_request = request;
  }

  /**
   * prepare script execution.
   *
   * @return this
   * @throws IllegalStateException if name null or empty also if script empty.
   * @throws InternalResourceNotFound if the resource is not there
   */
  @PostConstruct
  JythonService init() {
    LOG.info("Init Jython execution of: {}", m_scriptName);
    if(StringUtils.isEmpty(m_scriptName)) {
      throw new IllegalStateException("null or empty script name not allowed");
    }

    try(InputStream ins = JythonService.class.getResourceAsStream("/scripts/jython/" + m_scriptName)) {
      m_script = IOUtils.toString(ins, StandardCharsets.UTF_8);
      if(StringUtils.isEmpty(m_script)) {
        LOG.error("Empty script");
        throw new IllegalStateException("Empty script: " + m_scriptName);
      }
    } catch(IOException | NullPointerException x) {
      LOG.error("Empty script-name");
      throw new InternalResourceNotFound(m_scriptName);
    }

    return this;
  }

  private void done() {
    LOG.info("destroy script engine");
    try {
      m_python.cleanup();
      m_python.close();
    } catch(Exception x) {
      LOG.error("Ignored: " + x.getMessage());
    }
  }

  /**
   *
   * @param param the script parameters, may be null
   * @return currently empty
   * @throws ScriptError if the script reports an error
   */
  Optional<Object> execute(final Map<String,Object> param) {
    LOG.debug("Executing: {}",m_scriptName);
    m_python.set("log",LOG);
    m_python.set("request",m_request);
    if(null != param) {
      for(Map.Entry<String,Object> entry : param.entrySet()) {
        m_python.set(entry.getKey(),entry.getValue());
      }
    }

    try {
      try {
        m_python.exec(m_script);
      } catch(PyException x) {
        throw new ScriptError(x.getMessage());
      }

      return Optional.empty();
    } finally {
      done();
    }
  }
}
