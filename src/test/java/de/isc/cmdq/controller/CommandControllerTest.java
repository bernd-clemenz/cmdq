package de.isc.cmdq.controller;

import de.isc.cmdq.conf.ServiceConfig;
import de.isc.cmdq.conf.WebConfig;
import de.isc.cmdq.service.CmdImplTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfig.class, WebConfig.class})
@WebAppConfiguration
@DirtiesContext
public class CommandControllerTest {

  private static Logger LOG;

  /*
   * Select a specific logging configuration for testing.
   */
  @BeforeAll
  public static void beforeClass() {
    System.setProperty("log4j.configurationFile","log4j2-test.xml");
    LOG = LogManager.getLogger(CmdImplTest.class);
  }

  @Autowired
  WebApplicationContext wac;
  private MockMvc mockMvc;

  public CommandControllerTest() {}

  @BeforeEach
  public void before() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }


  @Test
  @DisplayName("Add command via controller")
  public void test002Status()
  throws Exception {
    LOG.info("add command via controller");
    mockMvc.perform(put("/cmd/12345678"))
           .andExpect(status().isCreated());
  }
}
