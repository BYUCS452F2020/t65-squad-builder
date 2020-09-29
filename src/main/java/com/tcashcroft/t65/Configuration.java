package com.tcashcroft.t65;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Spring configuration for the application.
 */
@org.springframework.context.annotation.Configuration
@AllArgsConstructor
public class Configuration {

    private Properties properties;

    @Bean("cardApiUri")
    public URI cardApiUri() {
        return URI.create(properties.getHarvester().getCardApiUri());
    }

    @Bean("dataRepoUri")
    public URI dataRepoUri() {
        return URI.create(properties.getHarvester().getXwingData2().getDataRepoUri());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Path dataRepoLocation() {
        return Paths.get(System.getProperty("java.io.tmpdir"), properties.getHarvester().getXwingData2().getDataRepoLocation());
    }

    @Bean
    public String actionsPath() {
        return properties.getHarvester().getXwingData2().getActionsPath();
    }

    @Bean
    public String factionsPath() {
        return properties.getHarvester().getXwingData2().getFactionsPath();
    }

    @Bean
    public String pilotsPath() {
        return properties.getHarvester().getXwingData2().getPilotsPath();
    }

    @Bean
    public String ffgXwsPath() {
        return properties.getHarvester().getXwingData2().getFfgXwsPath();
    }
}
