package com.tcashcroft.t65.cli;

import edu.byu.hbll.json.ObjectMapperFactory;
import edu.byu.hbll.json.UncheckedObjectMapper;
import lombok.Setter;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
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

  @Autowired
  private T65Client client;

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





//    TextIO textio = TextIoFactory.getTextIO();
//
//    TextTerminal terminal = textio.getTextTerminal();
//    terminal.printf("Starting up...\n");
//
//    String value = textio.newStringInputReader().withDefaultValue("none").read("Read");
//
//    terminal.printf("\nUser input was %s\n", value);
//    terminal.dispose();


    System.out.println("Ran");
    System.out.println(client.getSquadBuilderUrl());
  }
}
