package de.isc.cmdq.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Internal state error.
 * &copy; ISC Clemenz &amp; Weinbrecht GmbH
 */
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalError extends RuntimeException {
  /**
   * Constructor.
   * @param msg the error message
   */
  public InternalError(final String msg) {
    super(msg);
  }

  /**
   * Constructor.
   *
   * @param x the cause.
   */
  public InternalError(final Throwable x) {
    super(x);
  }
}
