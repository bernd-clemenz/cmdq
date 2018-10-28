package de.isc.cmdq.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Script execution error.
 * (c) ISC Clemenz &amp; Weinbrecht GmbH
 */
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class ScriptError extends RuntimeException {
  /**
   * Constructor.
   * @param msg the error message
   */
  public ScriptError(final String msg) {
    super(msg);
  }

  /**
   * Constructor.
   *
   * @param x the cause.
   */
  public ScriptError(final Throwable x) {
    super(x);
  }
}
