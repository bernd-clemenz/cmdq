package de.isc.cmdq.domain;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.UUID;

public class CmdRequestTest {
  public CmdRequestTest() {}

  @Test
  public void test001Constructor(){
    Assert.notNull(new CmdRequest(),"Construction failed");
  }

  @Test
  public void test002SetGetName() {
    CmdRequest req = new CmdRequest();
    final String name = "thisIsAName";
    req.setCmdName(name);
    Assertions.assertEquals(name,req.getCmdName(),"Name not equal");
  }
}
