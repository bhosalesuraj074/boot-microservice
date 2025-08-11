package com.withdraw;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WithdrawExceptions{

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> globalException(Exception e){
        System.out.println("Exception occurred: " + e.getMessage());
        return ResponseEntity.badRequest().body("Something went wrong...");
    }
}
