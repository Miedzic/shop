package com.miedzic.shop.controller;

import com.miedzic.shop.domain.dto.ErrorDto;
import com.miedzic.shop.domain.dto.FieldErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class AdviceController {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("not found", e);
        return new ErrorDto("not found");
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto handleSqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        log.error("entity with this value already exists", e);
        return new ErrorDto("value not unique");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<FieldErrorDto> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("not valid argument");
        // List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        //FieldError objectError = (FieldError) allErrors.get(0);
        //   objectError.getField();
        /*   List<FieldErrorDto> list = */
        return e.getBindingResult().getAllErrors().stream()
                .map(objectError -> {
                    FieldError fieldError = (FieldError) objectError;
                    return new FieldErrorDto(fieldError.getField(), fieldError.getDefaultMessage());
                })
                .collect(Collectors.toList());
    }
}
// object error nie jest field errorem :?
//jeśli object error nie jest field errorem to field na null