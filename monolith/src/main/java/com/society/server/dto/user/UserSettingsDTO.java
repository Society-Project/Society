package com.society.server.dto.user;

import com.society.server.model.entity.user.UserPersonalInfo;
import com.society.server.utils.validators.EmailValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSettingsDTO {

    @NotBlank(message = "First name is required.")
    @Size(min = 3, max = 15, message = "Please enter First name between 3 and 15 symbols.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(min = 3, max = 15, message = "Please enter Last name between 3 and 15 symbols.")
    private String lastName;

    @NotBlank(message = "Username is required.")
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 25, message = "Password must be at least 6 symbols.")
    private String password;
    @EmailValidator
    private String email;
    private UserPersonalInfo userPersonalInfo;
}
