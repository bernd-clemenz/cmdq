package de.isc.cmdq.service;

import de.isc.cmdq.conf.ServiceConfig;
import de.isc.cmdq.domain.CmdRequest;
import de.isc.cmdq.error.ScriptError;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;


@ExtendWith(SpringExtension.class)
@Tag("unitTest")
@ContextConfiguration(classes = {ServiceConfig.class})
@DirtiesContext
class JythonServiceTest {

  private static Logger LOG;

  /*
   * Select a specific logging configuration for testing.
   */
  @BeforeAll
  static void beforeClass() {
    System.setProperty("log4j.configurationFile","log4j2-test.xml");
    LOG = LogManager.getLogger(JythonServiceTest.class);
  }

  @Autowired private ApplicationContext m_app;

  JythonServiceTest() {}

  @Test
  @DisplayName("Check application context")
  void test001BeanExistence() {
    LOG.info("Application-context present");
    Assertions.assertNotNull(m_app);
  }

  @Test
  @DisplayName("Null script name")
  void test002ConstructorNullScriptName() {
    LOG.info("Null script name");
    Assertions.assertThrows(BeanCreationException.class,() ->
      m_app.getBean(JythonService.class,new Object[] {null, null})
    );
  }

  @Test
  @DisplayName("Empty script name")
  void test003ConstructorEmptyScriptName() {
    LOG.info("empty script name");
    Assertions.assertThrows(BeanCreationException.class,() ->
      m_app.getBean(JythonService.class,"", null)
    );
  }

  @Test
  @DisplayName("Empty script")
  void test004ConstructorEmptyScript() {
    LOG.info("empty script (no code)");
    Assertions.assertThrows(BeanCreationException.class,() ->
      m_app.getBean(JythonService.class,"empty.py", null)
    );
  }

  @Test
  @DisplayName("Not existend script")
  void test004ConstructorNotExistent() {
    LOG.info("Not existent script");
    Assertions.assertThrows(BeanCreationException.class,() ->
      m_app.getBean(JythonService.class,"not_existent_script.py", null)
    );
  }

  @Test
  @DisplayName("Hello-script")
  void test005HelloScript() {
    LOG.info("Hello world");
    m_app.getBean(JythonService.class,"hello.py", null)
         .execute(null);
  }

  @Test
  @DisplayName("Hello-script with parameters")
  void test006HelloScript() {
    LOG.info("Hello with parameter");
    m_app.getBean(JythonService.class,"hello_param.py", null)
         .execute(Map.of("user","Homer Simpson"));
  }

  @Test
  @DisplayName("Multiple hello-script with parameters")
  void test007HelloScriptMultipleTimes() {
    LOG.info("Hello with parameter");
    for(int i = 0; i < 50; ++i) {
      m_app.getBean(JythonService.class, "hello_param.py", null)
           .execute(Map.of("user", "Homer Simpson"));
    }

    StatisticsCollector stat = m_app.getBean(StatisticsCollector.class);
    LOG.info("Script calls: {}",stat.countScriptCalls());
  }

  @Test
  @DisplayName("Script with exception")
  void test007ScriptWithException() {
    LOG.info("Script with exception");
    Assertions.assertThrows(ScriptError.class,() ->
      m_app.getBean(JythonService.class,"error.py", null)
           .execute(Map.of("user","Homer Simpson"))
    );
  }
}
