package de.isc.cmdq.controller;

import de.isc.cmdq.conf.ServiceConfig;
import de.isc.cmdq.conf.WebConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Tag("unitTest")
@ContextConfiguration(classes = {ServiceConfig.class, WebConfig.class})
@WebAppConfiguration
@DirtiesContext
class CommandControllerTest {

  private static Logger LOG;

  /*
   * Select a specific logging configuration for testing.
   */
  @BeforeAll
  static void beforeClass() {
    System.setProperty("log4j.configurationFile","log4j2-test.xml");
    LOG = LogManager.getLogger(CommandControllerTest.class);
  }

  @Autowired private WebApplicationContext wac;
  private MockMvc mockMvc;

  CommandControllerTest() {}

  @BeforeEach
  void before() {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }


  @Test
  @DisplayName("Add command via controller")
  void test001Add()
  throws Exception {
    LOG.info("add command via controller");
    mockMvc.perform(put("/cmd/12345678"))
           .andExpect(status().isCreated());
  }

  @ParameterizedTest
  @ValueSource(strings = {"a", "b","c","d","e","f"})
  @DisplayName("Add multiple command via controller in sequence")
  void test002MultiAdd(final String value)
  throws Exception {
    LOG.info("multiple add command via controller in sequence");
    mockMvc.perform(put("/cmd/" + value))
           .andExpect(status().isCreated());
  }

  @Test
  @DisplayName("Add command via controller")
  void test003Add()
  throws Exception {
    LOG.info("add command via controller");
    mockMvc.perform(put("/cmd/12345678"))
            .andExpect(status().isCreated());
  }
}
