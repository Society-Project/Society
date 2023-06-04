package com.society.server.api.v1;

import com.society.server.dto.response.ResponseDTO;
import com.society.server.dto.user.UserGetPersInfoDTO;
import com.society.server.dto.user.UserProfileDTO;
import com.society.server.dto.user.UserSetPersInfoDTO;
import com.society.server.exception.AccountProblemException;
import com.society.server.exception.ResourceNotFoundException;
import com.society.server.service.UserService;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

import static com.society.server.config.AppConstants.API_BASE;

@RestController
@RequestMapping(path = API_BASE + "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDTO<UserProfileDTO>> getUserById(@PathVariable Long userId) {
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
    public ResponseEntity<ResponseDTO<UserGetPersInfoDTO>> getUserPersonalInfoByUserId(@PathVariable(name = "userId") Long userId) {
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

    @PutMapping("/edit/{userId}")
    public ResponseEntity<ResponseDTO<Void>> editUserInfo(@PathVariable(name = "userId") Long userId,
                                                          @Valid @RequestBody UserSetPersInfoDTO userDto,
                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity
                    .badRequest()
                    .body(
                            ResponseDTO
                                    .<Void>builder()
                                    .status(HttpStatus.BAD_REQUEST.value())
                                    .message(String.join(", ", errorMessages))
                                    .build()
                    );
        }
        userService.editUserInfo(userId, userDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDTO
                                .<Void>builder()
                                .status(HttpStatus.OK.value())
                                .message("Successfully update user settings.")
                                .content(null)
                                .build()
                );
    }

}
