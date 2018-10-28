package de.isc.cmdq.service;

import de.isc.cmdq.conf.ServiceConfig;
import de.isc.cmdq.domain.CmdRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@Tag("unitTest")
@ContextConfiguration(classes = {ServiceConfig.class})
@DirtiesContext
class CmdImplTest {

  private static Logger LOG;

  /*
   * Select a specific logging configuration for testing.
   */
  @BeforeAll
  static void beforeClass() {
    System.setProperty("log4j.configurationFile","log4j2-test.xml");
    LOG = LogManager.getLogger(CmdImplTest.class);
  }

  @Autowired private Cmd m_cmd;
  @Value("${cmdq.queue.size}") private int m_maxSize;

  CmdImplTest() {}

  @Test
  void test001BeanExistence() {
    Assertions.assertNotNull(m_cmd);
  }

  @Test
  void test002AddNull() {
    LOG.info("Add null to command queue");
    Assertions.assertThrows(NullPointerException.class,() -> {
      m_cmd.add(null);
    });
  }

  @Test
  void test003Add() {
    LOG.info("Add real objects to command queue");
    CmdRequest req = CmdRequest.builder().cmdName("name").build();
    String id = m_cmd.add(req);
    Assertions.assertNotNull(id);
  }

  @Test
  void test004Size() {
    LOG.info("Check size while processing");
    int sz = m_cmd.size();
    Assertions.assertEquals(0,sz);
    CmdRequest req = CmdRequest.builder().cmdName("name").build();
    String id = m_cmd.add(req);
    sz = m_cmd.size();
    Assertions.assertNotNull(id);
    Assertions.assertTrue(sz >= 0);
  }

  @Test
  void test005InitialSizeConfig() {
    Assertions.assertTrue(m_maxSize > 0);
  }

  @Test
  void test006StuffQueue() {
    LOG.info("push a lot stuff into the command queue");
    for(int i = 0; i < m_maxSize * 5; ++i) {
      CmdRequest req = CmdRequest.builder()
                                 .cmdName(RandomStringUtils.randomAlphanumeric(10))
                                 .build();
      m_cmd.add(req);
    }
    LOG.info("stuffing done");
  }
}
