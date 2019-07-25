package de.isc.cmdq.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Error in the configuration.
 *
 * &copy; ISC Clemenz &amp; Weinbrecht GmbH
 */
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class ConfigError extends RuntimeException {
  /**
   * Constructor.
   * @param msg the error message
   */
  public ConfigError(final String msg) {
    super(msg);
  }

  /**
   * Constructor.
   *
   * @param x the cause.
   */
  public ConfigError(final Throwable x) {
    super(x);
  }
}
