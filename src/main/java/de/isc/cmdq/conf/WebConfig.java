package de.isc.cmdq.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * &copy; ISC Clemenz &amp; Weinbrecht GmbH
 * Specific web configuration.
 *
 * @author Bernd Clemenz
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@EnableWebMvc
@ComponentScan(
        basePackages = {"de.isc.cmdq.controller"}
)
public class WebConfig implements WebMvcConfigurer {
  /**
   * Constructor.
   */
  public WebConfig() { /* empty */ }

  /**
   * @param configurer Spring-provided configurer
   */
  @Override
  public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
    configurer.defaultContentType(MediaType.APPLICATION_JSON)
              .parameterName("mediaType")
              .favorParameter(true)
              .ignoreAcceptHeader(true)
              .ignoreUnknownPathExtensions(true)
              .useRegisteredExtensionsOnly(true);
  }

 // @Override
 // public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
 //   configurer.enable();
 // }
}
