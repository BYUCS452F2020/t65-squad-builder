package com.tcashcroft.t65.cli;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/** Properties for the application. */
@Configuration
@ConfigurationProperties("tcashcroft.t65-cli")
@Data
public class T65CliProperties {

    private String squadBuilderUrl;
    private String username;
}