package com.tcashcroft.t65.controller;

import com.tcashcroft.t65.exception.BadRequestException;
import com.tcashcroft.t65.exception.NotFoundException;
import com.tcashcroft.t65.exception.UserExistsException;
import com.tcashcroft.t65.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    // TODO wrap the responses in objects

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public String getUser(@PathVariable final String username) throws NotFoundException {
        String retrievedUsername = userService.getUser(username);
        if (retrievedUsername == null || retrievedUsername.isBlank()) {
            throw new NotFoundException();
        } else {
            return retrievedUsername;
        }
    }

    @PostMapping("/{username}")
    public String createUser(@PathVariable final String username) throws BadRequestException {
        try {
            String createdUsername = userService.createUser(username);
            return createdUsername;
        } catch (UserExistsException e) {
            throw new BadRequestException();
        }
    }
}
