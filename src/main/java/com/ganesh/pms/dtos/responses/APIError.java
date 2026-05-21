package com.ganesh.pms.dtos.responses;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIError {
    private String message;
    private String path;
    private HttpStatus status;
    private LocalDateTime timestamp;
}
