package org.quizapp.loginservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRegisterDTO {
    private String email;
    private String rawPassword; // For input fra API
    private String firstName;
    private String lastName;
    private String role;
}
