package com.alexandrenolla.backendtechforb.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alexandrenolla.backendtechforb.models.Card;
import com.alexandrenolla.backendtechforb.models.Card.CreateCard;
import com.alexandrenolla.backendtechforb.models.Task;
import com.alexandrenolla.backendtechforb.services.CardService;
import com.alexandrenolla.backendtechforb.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/card")
@Validated
public class CardController {
    
    @Autowired
    private CardService cardService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Card>> findAllCards() {
    List<Card> cards = cardService.findAllCards();
    return ResponseEntity.ok().body(cards);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Card>> findAllByUserId(@PathVariable Long userId) {
        
        userService.findById(userId);
        
        List<Card> objs = this.cardService.findAllByUserId(userId);
        
        return ResponseEntity.ok().body(objs);
    }

    @PostMapping
    @Validated(CreateCard.class)
    public ResponseEntity<Void> create(@Valid @RequestBody Card obj) {

        this.cardService.create(obj);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

}
