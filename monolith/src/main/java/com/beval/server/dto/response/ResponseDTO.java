package com.beval.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {
    private String message;
    private T content;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
}
