package com.society.server.dto.user;

import com.society.server.utils.validators.EmailConditional;
import com.society.server.utils.validators.PasswordConditional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EmailConditional
@PasswordConditional
public class UserSetPersInfoDTO {

    @NotBlank(message = "First name is required.")
    @Size(min = 3, max = 15, message = "Please enter First name between 3 and 15 symbols.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(min = 3, max = 15, message = "Please enter Last name between 3 and 15 symbols.")
    private String lastName;

    @NotBlank(message = "Username is required.")
    private String username;

    private String password;
    private String newPassword;
    private String repeatNewPassword;
    private String email;
    private String newEmail;
    private String location;
    private String workPlace;
    private String education;
    private LocalDate birthday;
}
