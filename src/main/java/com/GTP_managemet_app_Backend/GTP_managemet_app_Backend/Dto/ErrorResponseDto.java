package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponseDto {
    private String path;
    private String message;
    private final OffsetDateTime timeStamp = OffsetDateTime.now();
}