package de.isc.cmdq.domain;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

/**
 * Item that is placed in the command queue.
 * (c) ISC Clemenz &amp; Weinbrecht GmbH
 */
@Data
@Builder
public class CmdQueueItem  {
  /**
   * Factory method.
   * @param request create item from the data of this request
   * @return the new item, with UUID set
   * @throws NullPointerException if request or request name is null
   * @throws IllegalArgumentException if request name is blank
   */
  public static CmdQueueItem from(final CmdRequest request) {
    return CmdQueueItem.builder()
                       .cmdRequest(request)
                       .build();
  }

  @NonNull private CmdRequest cmdRequest;
  @Builder.Default @Setter(AccessLevel.NONE) private Instant created = Instant.now();
  @NonNull @Builder.Default private UUID id = UUID.randomUUID();
}
