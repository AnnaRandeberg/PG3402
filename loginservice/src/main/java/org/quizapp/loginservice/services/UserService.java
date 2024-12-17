package org.quizapp.loginservice.services;

import lombok.RequiredArgsConstructor;
import org.quizapp.loginservice.eventdriven.UserEventPublisher;
import org.quizapp.loginservice.model.User;
import org.quizapp.loginservice.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserEventPublisher userPublisher;

    private final BCryptPasswordEncoder passwordEncoder;


    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("The email is already in use");
        }

        String hashedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);

        user.setRole("STUDENT");

        User savedUser = userRepository.save(user);

        userPublisher.publishUserCreatedEvent(savedUser);

        return savedUser;
    }

    public Optional<User> loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            System.out.println("Raw password: " + password);
            System.out.println("Hashed password from DB: " + user.get().getPasswordHash());

            if (passwordEncoder.matches(password, user.get().getPasswordHash())) {
                System.out.println("Password matches!");
                return user;
            } else {
                System.out.println("Password does not match.");
            }
        } else {
            System.out.println("User not found for email: " + email);
        }
        return Optional.empty();
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email); // checks in database
    }

    public List<User> searchUsersByFirstAndLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    }

