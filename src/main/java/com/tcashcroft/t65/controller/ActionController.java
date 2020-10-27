package com.tcashcroft.t65.controller;

import com.tcashcroft.t65.model.Action;
import com.tcashcroft.t65.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("action")
public class ActionController {

    @Autowired
    private ActionService actionService;

    @GetMapping("/{id}")
    public Action getAction(@PathVariable("id") final String id) {
        return actionService.getAction(id);
    }
}
