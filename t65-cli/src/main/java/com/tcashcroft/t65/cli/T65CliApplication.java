package com.tcashcroft.t65.cli;

import edu.byu.hbll.json.ObjectMapperFactory;
import edu.byu.hbll.json.UncheckedObjectMapper;
import lombok.Setter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Defines the entry point for the Spring Boot application and provides application context, such as
 * configuration, which can be injected into other Spring-managed classes.
 */
@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties
@Setter
public class T65CliApplication implements CommandLineRunner {

  /**
   * Launches this application.
   * 
   * @param args the command line arguments provided at runtime
   */
  public static void main(String[] args) {
    SpringApplication.run(T65CliApplication.class, args).close();
  }

  @Bean
  public UncheckedObjectMapper objectMapper() {
    return ObjectMapperFactory.newUnchecked();
  }

  @Override
  public void run(String... args) {
  }
}
