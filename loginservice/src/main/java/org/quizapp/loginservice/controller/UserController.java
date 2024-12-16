package org.quizapp.loginservice.controller;

import lombok.RequiredArgsConstructor;
import org.quizapp.loginservice.dtos.UserDTO;
import org.quizapp.loginservice.model.User;
import org.quizapp.loginservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        return userService.loginUser(user.getEmail(), user.getPasswordHash())
                .map(u -> {
                    if (u.getRole().equals("ADMIN")) {
                        return ResponseEntity.ok("Velcome admin, " + u.getFirstName() + "!");
                    } else {
                        return ResponseEntity.ok("Velcome, " + u.getFirstName() + "!");
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password."));
    }


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(user -> ResponseEntity.ok(new UserDTO(user.getEmail(), user.getRole())))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }





}