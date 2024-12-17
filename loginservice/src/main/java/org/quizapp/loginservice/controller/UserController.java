package org.quizapp.loginservice.controller;

import lombok.RequiredArgsConstructor;
import org.quizapp.loginservice.dtos.FlashcardDTO;
import org.quizapp.loginservice.dtos.UserDTO;
import org.quizapp.loginservice.dtos.UserRegisterDTO;
import org.quizapp.loginservice.model.User;
import org.quizapp.loginservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.quizapp.loginservice.client.FlashcardClient;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FlashcardClient flashcardClient;



    @GetMapping("/exists")
    public ResponseEntity<?> checkUserExists(@RequestParam String email) {
        boolean userExists = userService.existsByEmail(email);
        if (!userExists) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }


    //denne funker
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterDTO userRequest) {
        try {
            User user = new User();
            user.setEmail(userRequest.getEmail());
            user.setPasswordHash(userRequest.getRawPassword());
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setRole(userRequest.getRole() != null ? userRequest.getRole() : "STUDENT");

            userService.registerUser(user);
            return ResponseEntity.ok("User registered!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String rawPassword = loginRequest.get("rawPassword");

        return userService.loginUser(email, rawPassword)
                .map(u -> ResponseEntity.ok("Welcome, " + u.getFirstName()))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password."));
    }


//viser fornavn, etternavn og rolle navn
    //Denne funker
@GetMapping
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