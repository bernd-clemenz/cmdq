package de.isc.cmdq.controller;

import de.isc.cmdq.domain.ErrorDescriptor;
import de.isc.cmdq.error.InternalError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Transforms error output in generic error-message output.
 *
 * (c) ISC Clemenz &amp; Weinbrecht GmbH
 */
@ControllerAdvice
@SuppressWarnings("unused")
public class ErrorHandler {
  private static final Logger LOG = LogManager.getLogger(ErrorHandler.class);

  /**
   * Constructor.
   */
  public ErrorHandler() { /* empty */ }

  /**
   * Build an error descriptor and populate it.
   *
   * @param x the exception
   * @param req the original request
   * @return Wrapper for the generic error container.
   */
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ResponseEntity<ErrorDescriptor> errorHandler(final Exception x,
                                                      final HttpServletRequest req) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    if(x.getClass().isAnnotationPresent(ResponseStatus.class)) {
      ResponseStatus st = x.getClass().getAnnotation(ResponseStatus.class);
      if(st.code() != null) {
        status = st.code();
      } else if(st.value() != null) {
        status = st.value();
      }
    }

    HttpHeaders header = new HttpHeaders();
    header.setContentType(MediaType.APPLICATION_JSON);

    ErrorDescriptor err = ErrorDescriptor.builder()
                                         .message(x.getMessage())
                                         .url(req.getRequestURI())
                                         .status(status.value())
                                         .build();

    return new ResponseEntity<>(err,header,status);
  }
}
