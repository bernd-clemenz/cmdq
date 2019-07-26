package de.isc.cmdq.conf;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.*;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Configuration for the core.
 * &copy; ISC Clemenz &amp; Weinbrecht GmbH
 */
@Configuration
@EnableLoadTimeWeaving
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(
        basePackages = {
         "de.isc.cmdq.service"
        }
)
@PropertySource("classpath:/cmdq.properties")
public class ServiceConfig {
  /**
   * Constructor.
   */
  public ServiceConfig() { /* empty */ }

  public HttpClient httpClient()
  throws Exception {
    return HttpClientBuilder.create()
                            .setSSLSocketFactory(sslConnectionSocketFactory())
                            .addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor())
                            .addInterceptorFirst(new EmptySoapActionHeaderRemoverInterceptor())
                            .build();
  }

  public SSLConnectionSocketFactory sslConnectionSocketFactory()
  throws Exception {
    // NoopHostnameVerifier essentially turns hostname verification off as otherwise following error
    // is thrown: java.security.cert.CertificateException: No name matching localhost found
    return new SSLConnectionSocketFactory(sslContext(), NoopHostnameVerifier.INSTANCE);
  }

  public SSLContext sslContext()
  throws Exception {
    return SSLContextBuilder.create()
      .loadTrustMaterial(new TrustAllStrategy() {
        @Override
        public boolean isTrusted(final X509Certificate[] chain,
                                 final String authType)
        throws CertificateException {
          return true;
        }
      }).build();
    //.loadTrustMaterial(trustStore.getFile(), trustStorePassword.toCharArray()).build();
  }

  @Bean
  @SuppressWarnings("unused")
  public HttpComponentsMessageSender httpComponentsMessageSender()
  throws Exception {
    HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
    httpComponentsMessageSender.setHttpClient(httpClient());

    return httpComponentsMessageSender;
  }

  @Bean
  @SuppressWarnings("unused")
  public Jaxb2Marshaller marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    marshaller.setPackagesToScan("net.gcomputer.webservices");

    return marshaller;
  }

  @Bean
  @SuppressWarnings("unused")
  public WebServiceTemplate webServiceTemplate(final Jaxb2Marshaller marshaller,
                                               final WebServiceMessageSender messageSender) {
    WebServiceTemplate webServiceTemplate = new WebServiceTemplate(marshaller, marshaller);
    webServiceTemplate.setMessageSender(messageSender);

    return webServiceTemplate;
  }
}
