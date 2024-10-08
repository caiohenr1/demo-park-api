package com.caiohenrique.demo_park_api.web.exeption;

import com.caiohenrique.demo_park_api.exception.CpfUniqueViolationException;
import com.caiohenrique.demo_park_api.exception.EntityNotFoundException;
import com.caiohenrique.demo_park_api.exception.PasswordInvalidException;
import com.caiohenrique.demo_park_api.exception.UserNameUniqueViolationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> AccessDeniedException(
            AccessDeniedException ex,
            HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, ex.getMessage()));
    }


    // tratamento para ao buscar usuarios que o ID não existe
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> EntityNotFoundException(
            RuntimeException ex,
            HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    // tratamento de username unicos - tentativa de criação de um user que ja existe
    @ExceptionHandler({UserNameUniqueViolationException.class, CpfUniqueViolationException.class})
    public ResponseEntity<ErrorMessage> UniqueViolationException(
            RuntimeException ex,
            HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    // tratamento de update de password
    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorMessage> PasswordInvalidException(
            RuntimeException ex,
            HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }


    // tratamento para validação de campos:
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> MethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request,
            BindingResult result) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) inválidos", result));
    }


}
