package org.quizapp.loginservice.services;

import lombok.RequiredArgsConstructor;
import org.quizapp.loginservice.model.User;
import org.quizapp.loginservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String ADMIN_PASSWORD = "secret";

    public User registerUser(User user) {
        if (ADMIN_EMAIL.equals(user.getEmail())) {
            throw new RuntimeException("Cannot create user with admin email");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("The email is already in use");
        }

        user.setRole("STUDENT");
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String email, String password) {
        if (ADMIN_EMAIL.equals(email) && ADMIN_PASSWORD.equals(password)) {
            User adminUser = new User();
            adminUser.setEmail(ADMIN_EMAIL);
            adminUser.setFirstName("Admin");
            adminUser.setLastName("User");
            adminUser.setRole("ADMIN");
            return Optional.of(adminUser);
        }

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPasswordHash().equals(password)) {
            return user;
        }

        return Optional.empty();
    }
}
