package com.tcashcroft.t65.cli;

import com.tcashcroft.t65.cli.client.InventoryClient;
import com.tcashcroft.t65.cli.client.ShipClient;
import com.tcashcroft.t65.cli.client.SquadClient;
import com.tcashcroft.t65.cli.client.UpgradeClient;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Spring configuration for the application.
 */
@Configuration
@Setter
public class T65CliConfiguration {

    @Autowired
    private T65CliProperties properties;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public URI squadBuilderUrl() {
        return URI.create(properties.getSquadBuilderUrl());
    }

    @Bean
    public String username() {
        return properties.getUsername();
    }

}
