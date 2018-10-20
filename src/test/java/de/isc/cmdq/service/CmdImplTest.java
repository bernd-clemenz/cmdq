package de.isc.cmdq.service;

import de.isc.cmdq.conf.ServiceConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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

  }
}
