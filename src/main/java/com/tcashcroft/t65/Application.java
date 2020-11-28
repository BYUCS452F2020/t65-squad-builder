package com.tcashcroft.t65;

import edu.byu.hbll.json.ObjectMapperFactory;
import edu.byu.hbll.json.UncheckedObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Defines the entry point for the Spring Boot application and provides application context, such as
 * configuration, which can be injected into other Spring-managed classes.
 */
@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties
@EnableMongoRepositories
public class Application {
  
  /**
   * Launches this application.
   * 
   * @param args the command line arguments provided at runtime
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public UncheckedObjectMapper objectMapper() {
    return ObjectMapperFactory.newUnchecked();
  }
}
