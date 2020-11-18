package com.tcashcroft.t65;

import com.tcashcroft.t65.harvester.GameDataHarvester;
import edu.byu.hbll.json.ObjectMapperFactory;
import edu.byu.hbll.json.UncheckedObjectMapper;
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
//        return Paths.get(System.getProperty("java.io.tmpdir"), properties.getHarvester().getXwingData2().getDataRepoLocation());
        return Paths.get(properties.getHarvester().getXwingData2().getDownloadDir(), properties.getHarvester().getXwingData2().getDataRepoLocation());
    }

    @Bean
    public Path actionsPath() {
        return Paths.get(properties.getHarvester().getXwingData2().getActionsPath());
    }

    @Bean
    public Path factionsPath() {
        return Paths.get(properties.getHarvester().getXwingData2().getFactionsPath());
    }

    @Bean
    public Path pilotsDir() {
        return Paths.get(properties.getHarvester().getXwingData2().getPilotsDir());
    }

    @Bean
    public Path upgradesDir() {
        return Paths.get(properties.getHarvester().getXwingData2().getUpgradesDir());
    }

    @Bean
    public Path ffgXwsPath() {
        return Paths.get(properties.getHarvester().getXwingData2().getFfgXwsPath());
    }

    @Bean
    public UncheckedObjectMapper mapper() {
        return ObjectMapperFactory.newUnchecked();
    }

    @Bean
    public GameDataHarvester.GameDataHarvesterConfiguration gameDataHarvesterConfiguration() {
        GameDataHarvester.GameDataHarvesterConfiguration config = new GameDataHarvester.GameDataHarvesterConfiguration();
        config.setDataRepoUri(dataRepoUri());
        config.setDataRepoLocation(dataRepoLocation());
        config.setActionsPath(actionsPath());
        config.setFactionsPath(factionsPath());
        config.setPilotsPath(pilotsDir());
        config.setFfgXwsPath(ffgXwsPath());
        config.setMapper(mapper());
        return config;
    }
}
