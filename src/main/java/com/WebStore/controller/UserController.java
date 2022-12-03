package com.WebStore.controller;

import com.WebStore.entities.User;
import com.WebStore.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<Void> register(@Valid @RequestBody User user) {
        user.setRole("ROLE_USER");
        userService.createUser(user);

        return ResponseEntity.noContent().build();
    }
}
