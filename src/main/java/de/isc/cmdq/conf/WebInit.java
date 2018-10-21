package de.isc.cmdq.conf;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Initialize the web-application
 */
public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {
  private static final Logger LOG = LogManager.getLogger(WebInit.class);

  /**
   * Constructor.
   */
  public WebInit() { /* empty*/ }

  @Override
  public void onStartup(ServletContext servletContext)
  throws ServletException {
    LOG.info("Web-application init");
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] { "/v1" };
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[] { WebConfig.class };
  }

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class[] { ServiceConfig.class };
  }
}
