package de.isc.cmdq.controller;

import de.isc.cmdq.domain.ErrorDescriptor;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Transforms error output in generic error-message output.
 * <p>
 * &copy; ISC Clemenz &amp; Weinbrecht GmbH
 * </p>
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
    String message = x.getMessage();
    if(x.getClass().isAnnotationPresent(ResponseStatus.class)) {
      ResponseStatus st = x.getClass().getAnnotation(ResponseStatus.class);
      status = st.value();
      if(StringUtils.isNotBlank(st.reason())) {
        message = st.reason();
      }
    }

    if(StringUtils.isBlank(message)) {
      message = "No information available.";
    }

    HttpHeaders header = new HttpHeaders();
    header.setContentType(MediaType.APPLICATION_JSON);

    ErrorDescriptor err = ErrorDescriptor.builder()
                                         .message(message)
                                         .url(req.getRequestURI())
                                         .status(status.value())
                                         .build();

    return new ResponseEntity<>(err,header,status);
  }
}
