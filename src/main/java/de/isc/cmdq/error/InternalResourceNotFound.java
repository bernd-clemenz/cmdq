package de.isc.cmdq.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Internal resource not found error.
 * (c) ISC Clemenz &amp; Weinbrecht GmbH
 */
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR,
                reason = "A internal resource was not found, this might be a programming error.")
public class InternalResourceNotFound extends RuntimeException {
  /**
   * Constructor.
   * @param msg the error message
   */
  public InternalResourceNotFound(final String msg) {
    super(msg);
  }

  /**
   * Constructor.
   *
   * @param x the cause.
   */
  public InternalResourceNotFound(final Throwable x) {
    super(x);
  }
}
