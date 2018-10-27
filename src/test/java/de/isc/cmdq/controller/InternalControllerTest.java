package de.isc.cmdq.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfig.class, WebConfig.class})
@WebAppConfiguration
@DirtiesContext
public class InternalControllerTest {

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

  public InternalControllerTest() {}

  @BeforeEach
  public void before() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  @DisplayName("Check version controller")
  public void test001Version()
  throws Exception {
    LOG.info("version");
    mockMvc.perform(get("/internal/version"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
  }

  @Test
  @DisplayName("Check status endpoint")
  public void test002Status()
  throws Exception {
    LOG.info("status");
    mockMvc.perform(get("/internal/status"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
  }
}