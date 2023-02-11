package com.society.server.dto.payload;

import com.society.server.utils.validators.EmailValidator;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SignupDTO {
    @NotBlank
    private String username;
    @EmailValidator
    private String email;
    @NotBlank
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
}
