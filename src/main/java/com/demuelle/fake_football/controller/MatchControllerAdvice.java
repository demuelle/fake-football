package com.demuelle.fake_football.controller;

import com.demuelle.fake_football.exception.BadNicknameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MatchControllerAdvice {
    @ExceptionHandler(value = {BadNicknameException.class})
    public ResponseEntity<String> badNicknameError(BadNicknameException e) {
       return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
