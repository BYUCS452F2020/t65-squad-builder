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
       private XwingData2 xwingData2;
    }

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "xwing-data2")
    public static class XwingData2 {
        private String dataRepoUri;
        private String downloadDir;
        private String dataRepoLocation;
        private String actionsPath;
        private String factionsPath;
        private String pilotsDir;
        private String upgradesDir;
        private String ffgXwsPath;
    }
}