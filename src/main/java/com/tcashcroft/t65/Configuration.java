package com.tcashcroft.t65;

import com.tcashcroft.t65.harvester.GameDataHarvester;
import com.tcashcroft.t65.model.Squad;
import edu.byu.hbll.json.ObjectMapperFactory;
import edu.byu.hbll.json.UncheckedObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
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

    @Bean("upgradeCardApiUri")
    public URI upgradeCardApiUri() {
        return URI.create(properties.getHarvester().getUpgradeCardApiUri());
    }

    @Bean("shipCardApiUri")
    public URI shipCardApiUri() {
        return URI.create(properties.getHarvester().getShipcardApiUri());
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
        config.setPilotsDir(pilotsDir());
        config.setUpgradesDir(upgradesDir());
        config.setFfgXwsPath(ffgXwsPath());
        config.setMapper(mapper());
        return config;
    }

//    @Autowired
//    MongoTemplate mongoTemplate;
//
//    @Autowired
//    MongoMappingContext mongoMappingContext;
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void initIndicesAfterStartup() {
//        IndexOperations indexOps = mongoTemplate.indexOps(Squad.class);
//
//        IndexResolver resolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);
//        resolver.resolveIndexFor(Squad.class).forEach(indexOps::ensureIndex);
//    }
}
