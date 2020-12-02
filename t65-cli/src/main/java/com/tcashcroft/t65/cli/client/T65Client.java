package com.tcashcroft.t65.cli.client;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Data
public abstract class T65Client {

    @Autowired
    protected URI squadBuilderUrl;

    @Autowired
    protected String username;

    @Autowired
    protected RestTemplate restTemplate;

    protected static final String CONTEXT_ROOT = "t65-squad-builder";
}
