package de.isc.cmdq.service;

import de.isc.cmdq.conf.ServiceConfig;
import de.isc.cmdq.domain.CmdRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfig.class})
public class CmdImplTest {

  @Autowired private Cmd m_cmd;

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
}
