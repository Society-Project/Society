package com.society.server.api.v1;

import com.society.server.dto.response.ResponseDTO;
import com.society.server.dto.user.UserGetPersInfoDTO;
import com.society.server.dto.user.UserProfileDTO;
import com.society.server.dto.user.UserSetPersInfoDTO;
import com.society.server.exception.AccountProblemException;
import com.society.server.exception.ResourceNotFoundException;
import com.society.server.exception.handler.GlobalExceptionHandler;
import com.society.server.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.society.server.config.AppConstants.API_BASE;

@RestController
@RequestMapping(path = API_BASE + "/users")
public class UserController {

    private final UserService userService;
    private final GlobalExceptionHandler globalExceptionHandler;

    public UserController(UserService userService, GlobalExceptionHandler globalExceptionHandler) {
        this.userService = userService;
        this.globalExceptionHandler = globalExceptionHandler;
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

    @GetMapping("/edit/{userId}")
    public ResponseEntity<ResponseDTO<UserGetPersInfoDTO>> getUserPersonalInfoByUserId(@PathVariable(name = "userId") Long userId){
        try {
            UserGetPersInfoDTO userInfoById = userService.getUserInfoById(userId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            ResponseDTO
                                    .<UserGetPersInfoDTO>builder()
                                    .content(userInfoById)
                                    .status(HttpStatus.OK.value())
                                    .build()
                    );
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity
                    .status(ex.getStatus())
                    .body(
                            ResponseDTO
                                    .<UserGetPersInfoDTO>builder()
                                    .message(ex.getMessage())
                                    .status(ex.getStatus().value())
                                    .build()
                    );
        }
    }
    /*@PostMapping("/edit/{userId}")
    public ResponseEntity<ResponseDTO<UserSetPersInfoDTO>> editUserInfo(@PathVariable(name = "userId") Long userId,
                                                                        @Valid @RequestBody UserSetPersInfoDTO userDto){
        userService.editUserInfo(userId, userDto);

    }*/

}
