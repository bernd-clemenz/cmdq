package de.isc.cmdq.domain;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class CmdRequestTest {
  public CmdRequestTest() {}

  @Test
  public void test001Constructor(){
    Assert.notNull(new CmdRequest(""),"Construction failed");
  }

  @Test
  public void test002SetGetName() {
    final String name = "thisIsAName";
    CmdRequest req = CmdRequest.builder().cmdName(name).build();
    Assertions.assertEquals(name,req.getCmdName(),"Name not equal");
  }
}
