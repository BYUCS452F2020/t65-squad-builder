package com.tcashcroft.t65.cli;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/** Properties for the application. */
@Configuration
@ConfigurationProperties("byuhbll.t65-cli")
@Data
public class T65CliProperties {

  // TODO: If your application requires unique configuration, define the config properties here.
  //  If your configuration is particularly complex, you can define additional properties files.
}