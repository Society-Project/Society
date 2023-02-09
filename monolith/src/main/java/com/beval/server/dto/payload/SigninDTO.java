package com.beval.server.dto.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SigninDTO {
    @NotBlank
    private String usernameOrEmail;
    @NotBlank
    private String password;
}
