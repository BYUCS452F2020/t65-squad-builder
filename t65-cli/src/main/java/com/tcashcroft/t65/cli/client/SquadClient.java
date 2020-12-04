package com.tcashcroft.t65.cli.client;

import com.tcashcroft.t65.cli.model.Squad;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class SquadClient extends T65Client {

    public List<Squad> getSquads() {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "user", "{username}", "squad", "all").build(username);
        Squad[] squads = restTemplate.getForObject(uri.toString(), Squad[].class);
        return Arrays.asList(squads).stream().sorted((squad1, squad2) -> {
            int res = 0;
            if (squad1.getFaction() == null || squad2.getFaction() == null) {
                res = 0;
            } else {
                res = squad1.getFaction().compareTo(squad2.getFaction());
            }
            if (res == 0) {
                return squad1.getName().compareTo(squad2.getName());
            } else return res;
        }).collect(Collectors.toList());
    }

    public Squad getSquad(String name) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "user", "{username}", "squad", "{squadName}").build(username, name);
        Squad squad = restTemplate.getForObject(uri.toString(), Squad.class);
        return squad;
    }

    public Squad createSquad(String name) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "user", "{username}", "squad").build(username);
        Squad squad = new Squad();
        squad.setUsername(username);
        squad.setName(name);
        return restTemplate.postForObject(uri.toString(), squad, Squad.class);
    }

    public void deleteSquad(String squadName) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "user", "{username}", "squad", "{name}").build(username, squadName);
        restTemplate.delete(uri.toString());
    }

    public Squad.ShipEntry addShipToSquad(String squadName, String shipName) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "user", "{username}", "squad", "{name}", "ship", "{shipName}").build(username, squadName, shipName);
        ResponseEntity<Squad.ShipEntry> response = restTemplate.exchange(uri.toString(), HttpMethod.POST, HttpEntity.EMPTY, Squad.ShipEntry.class);
        return response.getBody();
//        return restTemplate.postForObject(uri.toString(), "", Squad.ShipEntry.class);
    }

    public Squad.ShipEntry getShipEntry(String squadName, String shipEntryId) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "user", "{username}", "squad", "{name}", "ship_entry", "{shipEntryId}").build(username, squadName, shipEntryId);
        return restTemplate.getForObject(uri.toString(), Squad.ShipEntry.class);
    }

    public Squad deleteShipEntry(String squadName, String shipEntryId) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "user", "{username}", "squad", "{name}", "ship_entry", "{shipEntryId}").build(username, squadName, shipEntryId);
        return restTemplate.postForObject(uri.toString(), null, Squad.class);
    }

    public List<Squad.ShipEntry> getSquadShips(String squadName) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "user", "{username}", "squad", "{name}", "ships").build(username, squadName);
        Squad.ShipEntry[] shipEntries = restTemplate.getForObject(uri.toString(), Squad.ShipEntry[].class);
        return Arrays.asList(shipEntries);
    }

    public Squad.ShipEntry addUpgradeToSquadShip(String squadName, String shipEntryId, String upgradeName) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "user", "{username}", "squad", "{name}", "ship_entry", "{shipEntryId}", "upgrade", "{upgradeName}").build(username, squadName, shipEntryId, upgradeName);
        return restTemplate.postForObject(uri.toString(), null, Squad.ShipEntry.class);
    }

    public Squad.ShipEntry deleteUpgradeFromShip(String squadName, String shipEntryId, String upgradeName) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "user", "{username}", "squad", "{name}", "ship_entry", "{shipEntryId}", "upgrade", "{upgradeName}").build(username, squadName, shipEntryId, upgradeName);
        ResponseEntity<Squad.ShipEntry> response = restTemplate.exchange(uri.toString(), HttpMethod.DELETE, HttpEntity.EMPTY, Squad.ShipEntry.class);
        return response.getBody();
    }
}
