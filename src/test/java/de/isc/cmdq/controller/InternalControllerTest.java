package de.isc.cmdq.controller;

import de.isc.cmdq.conf.ServiceConfig;
import de.isc.cmdq.conf.WebConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Tag("unitTest")
@ContextConfiguration(classes = {ServiceConfig.class, WebConfig.class})
@WebAppConfiguration
@DirtiesContext
class InternalControllerTest {

  private static Logger LOG;

  /*
   * Select a specific logging configuration for testing.
   */
  @BeforeAll
  static void beforeClass() {
    System.setProperty("log4j.configurationFile","log4j2-test.xml");
    LOG = LogManager.getLogger(InternalControllerTest.class);
  }

  @Autowired private WebApplicationContext wac;
  private MockMvc mockMvc;

  InternalControllerTest() {}

  @BeforeEach
  void before() {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  @DisplayName("Check version controller")
  void test001Version()
  throws Exception {
    LOG.info("version");
    mockMvc.perform(get("/internal/version"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("Check status endpoint")
  void test002Status()
  throws Exception {
    LOG.info("status");
    mockMvc.perform(get("/internal/status"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }
}
