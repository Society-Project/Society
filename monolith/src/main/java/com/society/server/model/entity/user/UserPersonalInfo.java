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
    private String location;
    private String workPlace;
    private String education;
    private LocalDate birthday;
}
