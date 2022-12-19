package de.isc.cmdq.service;

import net.gcomputer.webservices.TodaysDilbert;
import net.gcomputer.webservices.TodaysDilbertResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * connecting to the daily dilbert SOAP-Service
 *
 * &copy; ISC Clemenz &amp; Weinbrecht GmbH
 *
 */
@Component
public class DailyDilbertServiceConnector {
  private static final Logger LOG = LogManager.getLogger(DailyDilbertServiceConnector.class);
  private final WebServiceTemplate m_webServiceTemplate;

  public DailyDilbertServiceConnector(final WebServiceTemplate webServiceTemplate) {
    m_webServiceTemplate = webServiceTemplate;
  }

  public String todaysDilbert() {
    LOG.info("Todays dilbert is called.");
    TodaysDilbert today = new TodaysDilbert();
    TodaysDilbertResponse rsp = (TodaysDilbertResponse) m_webServiceTemplate.marshalSendAndReceive("http://www.gcomputer.net/webservices/dilbert.asmx",today);
    return rsp.getTodaysDilbertResult();
  }
}
