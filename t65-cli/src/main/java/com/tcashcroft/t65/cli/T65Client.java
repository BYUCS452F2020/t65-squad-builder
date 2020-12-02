package com.tcashcroft.t65.cli;

import com.tcashcroft.t65.cli.model.Inventory;
import com.tcashcroft.t65.cli.model.Ship;
import com.tcashcroft.t65.cli.model.Squad;
import com.tcashcroft.t65.cli.model.Upgrade;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@Data
public class T65Client {

    @Autowired
    private URI squadBuilderUrl;

    @Autowired
    private String username;

    @Autowired
    private RestTemplate restTemplate;

    private static final String CONTEXT_ROOT = "t65-squad-builder";
    private static final String INVENTORY_TEMPLATE = String.format("%s/inventory/user/{username}", CONTEXT_ROOT);
    private static final String SQUAD_TEMPLATE = String.format("%s/user/{username}/squad", CONTEXT_ROOT);
    private static final String SHIP_TEMPLATE = String.format("%s/ship", CONTEXT_ROOT);
    private static final String UPGRADE_TEMPLATE = String.format("%s/upgrade", CONTEXT_ROOT);

    public Inventory getInventory() {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).path(INVENTORY_TEMPLATE).build(username);
        System.out.println("URI: " + uri.toString());
        try {
            Inventory inventory = restTemplate.getForObject(uri.toString(), Inventory.class);
            return inventory;
        } catch (HttpClientErrorException.NotFound e) {
            return restTemplate.postForObject(uri.toString(), null, Inventory.class);
        }
    }

    public Inventory addShipToInventory(String shipName) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(INVENTORY_TEMPLATE, "ship/{shipName}").build(username, shipName);
        Inventory inventory = restTemplate.postForObject(uri.toString(), null, Inventory.class);
        return inventory;
    }

    public Squad getSquad(String name) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(SQUAD_TEMPLATE, "/{squadName}").build(username, name);
        Squad squad = restTemplate.getForObject(uri.toString(), Squad.class);
        return squad;
    }

    public Squad updateSquad(Squad squad) {
        return null;
    }

    public void deleteSquad(Squad squad) {
    }

    public Ship getShip(String shipName) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(SHIP_TEMPLATE, "/{shipName}").build(username, shipName);
        Ship ship = restTemplate.getForObject(uri.toString(), Ship.class);
        return ship;
    }

    public List<Ship> getShips() {
        return null;
    }

    public List<Ship> getShips(Object criteria) {
        return null;
    }

    public Upgrade getUpgrade(String upgradeName) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(UPGRADE_TEMPLATE, "/{upgradeName}").build(username, upgradeName);
        Upgrade upgrade = restTemplate.getForObject(uri.toString(), Upgrade.class);
        return upgrade;
    }

    public List<Upgrade> getUpgrades() {
        return null;
    }

    public List<Upgrade> getUpgrades(Object criteria) {
        return null;
    }

}
