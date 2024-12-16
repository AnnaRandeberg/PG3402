package org.quizapp.loginservice.controller;

import lombok.RequiredArgsConstructor;
import org.quizapp.loginservice.dtos.FlashcardDTO;
import org.quizapp.loginservice.dtos.UserDTO;
import org.quizapp.loginservice.model.User;
import org.quizapp.loginservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.quizapp.loginservice.client.FlashcardClient;

import java.util.List;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FlashcardClient flashcardClient;

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

//viser kun fornavn, etternavn og rolle navn
@GetMapping("/api/users")
public ResponseEntity<List<UserDTO>> getAllUsers() {
    List<UserDTO> userDTOs = userService.getAllUsers()
            .stream()
            .map(user -> new UserDTO(
                    user.getFirstName(),
                    user.getLastName(),
                    user.getRole()
            ))
            .toList();

    return ResponseEntity.ok(userDTOs);
}

    @GetMapping("/search/{firstName}/{lastName}")
    public ResponseEntity<List<UserDTO>> searchUsers(
            @PathVariable String firstName,
            @PathVariable String lastName) {

        List<UserDTO> users = userService.searchUsersByFirstAndLastName(firstName, lastName)
                .stream()
                .map(user -> new UserDTO(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getRole()))
                .toList();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/flashcards/{quizId}")
    public ResponseEntity<List<FlashcardDTO>> getFlashcardsForQuiz(@PathVariable Long quizId) {
        List<FlashcardDTO> flashcards = flashcardClient.getFlashcards(quizId);
        return ResponseEntity.ok(flashcards);
    }


}