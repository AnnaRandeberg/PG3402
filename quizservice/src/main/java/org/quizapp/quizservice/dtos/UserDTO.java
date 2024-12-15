package org.quizapp.quizservice.dtos;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private String email;
    private String role;


}
