package com.society.server.api.v1;

import com.society.server.dto.response.ResponseDTO;
import com.society.server.dto.user.UserProfileDTO;
import com.society.server.exception.AccountProblemException;
import com.society.server.exception.ResourceNotFoundException;
import com.society.server.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.society.server.config.AppConstants.API_BASE;

@RestController
@RequestMapping(path = API_BASE + "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDTO<UserProfileDTO>> getUserById(@PathVariable Long userId){
        try {
            UserProfileDTO userDTO = userService.getUserById(userId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            ResponseDTO
                                    .<UserProfileDTO>builder()
                                    .content(userDTO)
                                    .status(HttpStatus.OK.value())
                                    .build()
                    );
        } catch (AccountProblemException | ResourceNotFoundException ex) {
            return ResponseEntity
                    .status(ex.getStatus())
                    .body(
                            ResponseDTO
                                    .<UserProfileDTO>builder()
                                    .message(ex.getMessage())
                                    .status(ex.getStatus().value())
                                    .build()
                    );
        }
    }


}
