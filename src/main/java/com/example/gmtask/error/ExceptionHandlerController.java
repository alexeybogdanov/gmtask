package com.example.gmtask.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CsvItemNotFoundException.class)
    public ErrorInfo handleItemNotFound(RuntimeException ex, HttpServletRequest request) {
        log.error("Request: {} raised: {} ", request.getRequestURL(), ex.getLocalizedMessage());
        return buildErrorInfo(ex, request);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(CsvParseException.class)
    public ErrorInfo handleCSVParse(RuntimeException ex, HttpServletRequest request) {
        log.error("Request: {} raised: {} ", request.getRequestURL(), ex.getLocalizedMessage());
        return buildErrorInfo(ex, request);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(Exception ex, HttpServletRequest request) {
        log.error("Request: {} raised: {} ", request.getRequestURL(), ex.getLocalizedMessage());
        return buildErrorInfo(ex, request);
    }

    private ErrorInfo buildErrorInfo(Exception ex, HttpServletRequest request) {
        return ErrorInfo.builder()
                .url(request.getRequestURL().toString())
                .details(ex.getLocalizedMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }
}
