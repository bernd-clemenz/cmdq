package de.isc.cmdq.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unitTest")
class CmdQueueItemTest {
  CmdQueueItemTest() { /* empty */ }

  @Test
  void testStaticConstructor() {
    final String name = "thisIsaName";
    CmdRequest request = CmdRequest.builder().cmdName(name).build();
    CmdQueueItem item = CmdQueueItem.from(request);

    Assertions.assertNotNull(item);
    Assertions.assertEquals(name,item.getCmdRequest().getCmdName());
    Assertions.assertNotNull(item.getId());
  }

  @Test
  void testStaticConstructorNull() {
     Assertions.assertThrows(NullPointerException.class,() -> {
      CmdQueueItem.from(null);
    });
  }

}
