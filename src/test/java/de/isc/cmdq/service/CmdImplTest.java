package de.isc.cmdq.service;

import de.isc.cmdq.conf.ServiceConfig;
import de.isc.cmdq.domain.CmdRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfig.class})
@DirtiesContext
public class CmdImplTest {

  @Autowired private Cmd m_cmd;
  @Value("${cmdq.queue.size}") private int m_maxSize;

  public CmdImplTest() {}

  @Test
  public void test001BeanExistence() {
    Assertions.assertNotNull(m_cmd);
  }

  @Test
  public void test002AddNull() {
    Assertions.assertThrows(NullPointerException.class,() -> {
      m_cmd.add(null);
    });
  }

  @Test
  public void test003Add() {
    CmdRequest req = new CmdRequest();
    req.setCmdName("name");
    String id = m_cmd.add(req);
    Assertions.assertNotNull(id);
  }

  @Test
  public void test004Size() {
    int sz = m_cmd.size();
    Assertions.assertTrue(sz == 0);
    CmdRequest req = new CmdRequest();
    req.setCmdName("name");
    String id = m_cmd.add(req);
    sz = m_cmd.size();
    Assertions.assertTrue(sz == 1);
  }

  @Test
  public void test005InitialSizeConfig() {
    Assertions.assertTrue(m_maxSize > 0);
  }
}
