package com.tcashcroft.t65.cli.client;

import com.tcashcroft.t65.cli.model.Ship;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@Data
public class ShipClient extends T65Client {

    private final Comparator<Ship> shipComparator = (Ship ship1, Ship ship2) -> ship1.getNameId().compareTo(ship2.getNameId());

    public Ship getShip(String shipName) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "ship", "{shipName}").build(shipName);
        Ship ship = restTemplate.getForObject(uri.toString(), Ship.class);
        return ship;
    }

    public List<Ship> getShips() {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "ship", "all").build().toUri();
        Ship[] shipArray = restTemplate.getForObject(uri.toString(), Ship[].class);
        return Stream.of(shipArray).sorted(shipComparator).collect(Collectors.toList());
    }

    public List<Ship> getShips(String filter, String id) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "ship", "{filter}", "{id}").build(filter, id);
        Ship[] shipArray = restTemplate.getForObject(uri.toString(), Ship[].class);
        return Arrays.asList(shipArray).stream().sorted(shipComparator).collect(Collectors.toList());
    }

    public List<String> getFactions() {
        return getShips().stream().map(Ship::getFaction).sorted().distinct().collect(Collectors.toList());
    }

    public List<String> getShipTypes() {
        return getShips().stream().map(Ship::getShipType).sorted().distinct().collect(Collectors.toList());
    }
}
