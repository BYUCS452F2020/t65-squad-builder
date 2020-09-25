package com.tcashcroft.t65;

import com.tcashcroft.t65.harvester.CardImageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Data
@AllArgsConstructor
@RestController
public class TempController {

    @Autowired
    private CardImageService cardImageRepository;

    @GetMapping("{cardId}")
    public String getCardImageUri(@PathVariable("cardId") final String cardId) {
        System.out.println("Card ID Requested: " + cardId);
       return cardImageRepository.getCardImageUri(cardId);
    }
}
