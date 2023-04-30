package com.society.server.model.entity.user;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserPersonalInfo {
    private LocalDate birthday;
    private String workPlace;
    private String homeTown;
    private String residence;
    private String country;
    private String highSchool;
    private String university;
}
