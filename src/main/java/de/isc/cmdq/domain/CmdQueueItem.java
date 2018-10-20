package de.isc.cmdq.domain;

import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.util.UUID;

@Data
public class CmdQueueItem  {
  /**
   * Factory method.
   * @param request create item from the data of this request
   * @return the new item, with UUID set
   * @throws NullPointerException if request or request name is null
   * @throws IllegalArgumentException if request name is blank
   */
  public static CmdQueueItem from(final CmdRequest request) {
    Validate.notNull(request, "null requests are not allowed");
    Validate.notBlank(request.getCmdName(),"Empty command names are not allowed");
    CmdQueueItem item = new CmdQueueItem();
    item.setCmdRequest(request);
    item.setId(UUID.randomUUID());

    return item;
  }

  private CmdRequest cmdRequest;
  private UUID id;

  /**
   * Constructor.
   */
  public CmdQueueItem() { /* empty */ }
}
