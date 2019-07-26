package de.isc.cmdq.service;

import de.isc.cmdq.conf.ServiceConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@Tag("unitTest")
@ContextConfiguration(classes = {ServiceConfig.class})
@DirtiesContext
class DailyDilbertServiceTest {

  private static Logger LOG;

  /*
   * Select a specific logging configuration for testing.
   */
  @BeforeAll
  static void beforeClass() {
    System.setProperty("log4j.configurationFile","log4j2-test.xml");
    LOG = LogManager.getLogger(DailyDilbertServiceTest.class);
  }

  @Autowired private ApplicationContext m_app;

  DailyDilbertServiceTest() {}

  @Test
  @DisplayName("Check application context")
  void test001BeanExistence() {
    LOG.info("Application-context present");
    Assertions.assertNotNull(m_app);
  }

  @Test
  @DisplayName("DailyDilbert call")
  void test002DilberCall() {
    LOG.info("DailyDilbert call");
    DailyDilbertServiceConnector srvc = m_app.getBean(DailyDilbertServiceConnector.class);
    LOG.info(srvc.todaysDilbert());
  }


}
