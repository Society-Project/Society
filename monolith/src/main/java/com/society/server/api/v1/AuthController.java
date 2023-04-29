package com.society.server.api.v1;

import com.society.server.dto.payload.SigninDTO;
import com.society.server.dto.payload.SignupDTO;
import com.society.server.dto.response.JwtResponseDTO;
import com.society.server.dto.response.ResponseDTO;
import com.society.server.exception.InvalidCredentialsException;
import com.society.server.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.society.server.config.AppConstants.API_BASE;

@RestController
@RequestMapping(path = API_BASE)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService userService) {
        this.authService = userService;
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<ResponseDTO<JwtResponseDTO>> signIn(@Valid @RequestBody SigninDTO signinDto) {

        try {
            String token = authService.signInUser(signinDto);
            JwtResponseDTO jwtResponseDTO = new JwtResponseDTO(token, "Bearer");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            ResponseDTO
                                    .<JwtResponseDTO>builder()
                                    .message("Logged in successfully!")
                                    .content(jwtResponseDTO)
                                    .status(HttpStatus.OK.value())
                                    .build()
                    );
        } catch (InvalidCredentialsException ex) {
            return ResponseEntity
                    .status(ex.getStatus())
                    .body(
                            ResponseDTO
                                    .<JwtResponseDTO>builder()
                                    .message(ex.getMessage())
                                    .status(ex.getStatus().value())
                                    .build()
                    );
        }
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<ResponseDTO<Object>> signUp(@Valid @RequestBody SignupDTO signupDto) {
        authService.signUpUser(signupDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ResponseDTO
                                .builder()
                                .message("Signed up successfully!")
                                .content(null)
                                .status(HttpStatus.CREATED.value())
                                .build()
                );
    }
}
