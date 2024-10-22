package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Exception;

import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {NotFoundException.class})
    ResponseEntity<ErrorResponseDto> handleReaderFailure(
            RuntimeException exception, HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(
                new ErrorResponseDto(httpServletRequest.getRequestURI(), exception.getMessage()),
                HttpStatus.NOT_FOUND);
    }
}
