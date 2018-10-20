package de.isc.cmdq.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class CmdQueueItemTest {
  public CmdQueueItemTest() { /* empty */ }

  @Test
  public void testConstructor() {
    Assertions.assertNotNull(new CmdQueueItem());
  }

  @Test
  public void test001UUID() {
    CmdQueueItem item = new CmdQueueItem();
    UUID id = UUID.randomUUID();

    item.setId(id);
    Assertions.assertEquals(id,item.getId());
  }

  @Test
  public void testStaticConstructor() {
    final String name = "thisIsaName";
    CmdRequest request = new CmdRequest();
    request.setCmdName(name);
    CmdQueueItem item = CmdQueueItem.from(request);

    Assertions.assertNotNull(item);
    Assertions.assertEquals(name,item.getCmdRequest().getCmdName());
    Assertions.assertNotNull(item.getId());
  }

  @Test
  public void testStaticConstructorNull() {
     Assertions.assertThrows(NullPointerException.class,() -> {
      CmdQueueItem.from(null);
    });
  }

  @Test
  public void testStaticConstructorRequestNameNull() {
    Assertions.assertThrows(NullPointerException.class,() -> {
      CmdQueueItem.from(new CmdRequest());
    });
  }
}
