package de.isc.cmdq.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

@Tag("unitTest")
class CmdRequestTest {
  CmdRequestTest() {}

  @Test
  void test001Constructor(){
    Assert.notNull(new CmdRequest(""),"Construction failed");
  }

  @Test
  void test002SetGetName() {
    final String name = "thisIsAName";
    CmdRequest req = CmdRequest.builder().cmdName(name).build();
    Assertions.assertEquals(name,req.getCmdName(),"Name not equal");
  }

  @Test
  void testStaticConstructorRequestNameNull() {
    Assertions.assertThrows(NullPointerException.class,() ->
      CmdRequest.builder().cmdName(null).build()
    );
  }
}
