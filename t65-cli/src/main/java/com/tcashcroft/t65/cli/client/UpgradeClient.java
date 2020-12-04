package com.tcashcroft.t65.cli.client;

import com.tcashcroft.t65.cli.model.Upgrade;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Data
public class UpgradeClient extends T65Client {

    public Upgrade getUpgrade(String upgradeName) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "upgrade", "{upgradeName}").build(upgradeName);
        Upgrade upgrade = restTemplate.getForObject(uri.toString(), Upgrade.class);
        return upgrade;
    }

    public List<Upgrade> getUpgrades() {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "upgrade", "all").build().toUri();
        Upgrade[] upgradeArray = restTemplate.getForObject(uri.toString(), Upgrade[].class);
        return Stream.of(upgradeArray).sorted((u1, u2) -> u1.getNameId().compareTo(u2.getNameId())).collect(Collectors.toList());
    }

    public List<Upgrade> getUpgrades(String filter, String id) {
        URI uri = UriComponentsBuilder.fromUri(squadBuilderUrl).pathSegment(CONTEXT_ROOT, "upgrade", "{filter}", "{id}").build(filter, id);
        Upgrade[] upgradeArray = restTemplate.getForObject(uri.toString(), Upgrade[].class);
        return Stream.of(upgradeArray).sorted().collect(Collectors.toList());
    }
//    public List<Upgrade> getUpgrades(Object criteria) {
//        return null;
//    }
}
