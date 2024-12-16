package org.quizapp.loginservice.services;

import lombok.RequiredArgsConstructor;
import org.quizapp.loginservice.eventdriven.UserEventPublisher;
import org.quizapp.loginservice.model.User;
import org.quizapp.loginservice.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserEventPublisher userPublisher;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("The email is already in use");
        }

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));

        user.setRole("STUDENT");

        User savedUser = userRepository.save(user);

        userPublisher.publishUserCreatedEvent(savedUser);

        return savedUser;
    }

    public Optional<User> loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPasswordHash())) {
            return user;
        }
        return Optional.empty();
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
