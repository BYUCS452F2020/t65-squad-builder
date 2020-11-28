/*
FFG just released a new version of their API that totally revamps the data model paradigm they were using, rendering
this class purposeless.

 */


//package com.tcashcroft.t65.harvester;
//
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import edu.byu.hbll.misc.Streams;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import javax.annotation.PostConstruct;
//import java.net.URI;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Data
//@AllArgsConstructor
//@Service
//@Slf4j
//public class CardImageService {
//
//    private URI upgradeCardApiUri;
//
//    private URI shipCardApiUri;
//
//    private RestTemplate restTemplate;
//
//    private Map<String, String> idToImageUri;
//
//    @PostConstruct
//    public void postConstruct() {
//        createIdToCardImageUri();
//    }
//
//    @Scheduled(cron = "0 15 3 * * 2")
//    public void createIdToCardImageUri() {
//        log.info("Building Card Image cache");
//        ObjectNode node = restTemplate.getForObject(upgradeCardApiUri.toString(), ObjectNode.class);
//        idToImageUri = Streams.of(node.get("cards")).map(n -> {
//            StringTuple t = new StringTuple();
//            t.setKey(n.path("ffg_id").asText());
//            t.setValue(n.path("card_image").asText());
//            return t;
//        }).collect(Collectors.toMap(StringTuple::getKey, StringTuple::getValue));
//
//        ObjectNode pilotNode = restTemplate.getForObject(shipCardApiUri.toString(), ObjectNode.class);
//        idToImageUri.putAll(Streams.of(node.get("cards")).map(n -> {
//            StringTuple t = new StringTuple();
//            t.setKey(n.path("ffg_id").asText());
//            t.setValue(n.path("card_image").asText());
//            return t;
//        }).collect(Collectors.toMap(StringTuple::getKey, StringTuple::getValue)));
//    }
//
//    public String getCardImageUri(String cardId) {
//        return idToImageUri.get(cardId);
//    }
//
//    @Data
//    public static class StringTuple {
//        private String key, value;
//    }
//}
