package com.tcashcroft.t65.cli.client;

import com.tcashcroft.t65.cli.model.Inventory;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Data
public class InventoryClient extends T65Client {

    public Inventory getInventory() {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "inventory", "user", "{username}").build(username);
        try {
            Inventory inventory = restTemplate.getForObject(uri.toString(), Inventory.class);
            return inventory;
        } catch (HttpClientErrorException.NotFound e) {
            return restTemplate.postForObject(uri.toString(), null, Inventory.class);
        }
    }

    public Inventory addShipToInventory(String shipName) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "inventory", "user", "{username}", "ship", "{shipName}").build(username, shipName);
        Inventory inventory = restTemplate.postForObject(uri.toString(), null, Inventory.class);
        return inventory;
    }

    public Inventory removeShipFromInventory(String shipName) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "inventory", "user", "{username}", "ship", "{shipName}").build(username, shipName);
        ResponseEntity<Inventory> response = restTemplate.exchange(uri.toString(), HttpMethod.DELETE, HttpEntity.EMPTY, Inventory.class);
        return response.getBody();
    }

    public Inventory addUpgradeToInventory(String upgradeName) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "inventory", "user", "{username}", "upgrade", "{upgradeName}").build(username, upgradeName);
        Inventory inventory = restTemplate.postForObject(uri.toString(), null, Inventory.class);
        return inventory;
    }

    public Inventory removeUpgradeFromInventory(String upgradeName) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "inventory", "user", "{username}", "upgrade", "{upgradeName}").build(username, upgradeName);
        ResponseEntity<Inventory> response = restTemplate.exchange(uri.toString(), HttpMethod.DELETE, HttpEntity.EMPTY, Inventory.class);
        return response.getBody();
    }
}
