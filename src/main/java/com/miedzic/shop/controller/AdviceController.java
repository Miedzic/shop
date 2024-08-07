package com.miedzic.shop.controller;

import com.miedzic.shop.domain.dto.ErrorDto;
import com.miedzic.shop.domain.dao.FieldErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

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
                    if(objectError instanceof FieldError fieldError){
                        return new FieldErrorDto(fieldError.getField(), fieldError.getDefaultMessage());
                    }
                    return new FieldErrorDto(null,objectError.getDefaultMessage());
                })
                .toList();
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleConstraintViolationException(ConstraintViolationException e){
        log.error("not valid argument",e);
        return new ErrorDto(e.getMessage());
    }
    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleMissingServletRequestPartException(MissingServletRequestPartException e){
        log.warn("required request part is not present",e);
        return new ErrorDto(e.getMessage());
    }
}
// object error nie jest field errorem :?
//jeśli object error nie jest field errorem to field na null