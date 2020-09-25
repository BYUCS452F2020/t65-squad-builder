package com.tcashcroft.t65;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

/** Properties for the application. */
@Configuration
@ConfigurationProperties("tcashcroft.t65-squad-builder")
@Data
public class Properties {

    private HarvesterProperties harvester;

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "harvester")
    public static class HarvesterProperties {
       private String cardApiUri;
    }
}