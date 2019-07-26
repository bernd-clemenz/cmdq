package de.isc.cmdq.conf;

import org.apache.http.*;
import org.apache.http.protocol.HttpContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EmptySoapActionHeaderRemoverInterceptor implements HttpRequestInterceptor {
  private static final Logger LOG = LogManager.getLogger(EmptySoapActionHeaderRemoverInterceptor.class);
  public EmptySoapActionHeaderRemoverInterceptor() {
    // empty
  }

  @Override
  public void process(HttpRequest httpRequest, HttpContext httpContext)
  throws HttpException,
         IOException {
    final String actionHeader = "SOAPAction";
    List<String> invalidSoapActions = Arrays.asList(null,"","\"\"");
    if(httpRequest instanceof HttpEntityEnclosingRequest) {
        Header[] allAction = httpRequest.getHeaders("SOAPAction");
        for(Header hd : allAction) {
          HeaderElement[] elements = hd.getElements();
          String value = hd.getValue();
          if(invalidSoapActions.contains(value)) {
            httpRequest.removeHeader(hd);
          }

          LOG.info("SOAPAction: {}",value);
        }
        //httpRequest.removeHeaders("Transfer-Encoding");

    }
  }
}
