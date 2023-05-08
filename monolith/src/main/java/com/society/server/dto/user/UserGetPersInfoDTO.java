package com.society.server.dto.user;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserGetPersInfoDTO {
    private String firstName;
    private String lastName;
    private String location;
    private String workPlace;
    private String education;
    private LocalDate birthday;
    private String email;
    private String username;
}
