package com.tcashcroft.t65;

import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

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

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }



    // TODO: If your application requires unique configuration, define the config beans here.  If your
    //  configuration is particularly complex, you can define additional config files.
}
