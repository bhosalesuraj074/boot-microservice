package com.account.controller;

import com.account.dao.User;
import com.account.services.AccountService;
import com.account.utility.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:4200/")
public class AccountController {
    @Autowired
    private AccountService service;

    //get the all user details
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<User>>> getAllUser(){

        List<User> users = service.loadAllUsers();
        if (users.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(404, "No user found"));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("users found", users));

    }

    @GetMapping("/get/{id}")
    private ResponseEntity<ApiResponse<User>> getUserById(@PathVariable long id){
        User user = service.loadUserById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("user found having name: "+ user.getFullName(),  user));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<User>> createAccount(@RequestBody @Valid User user){
        User newUser = service.save(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("New Account created successfully", newUser));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiResponse<User>> updateUserDetails (@PathVariable long id,@RequestBody @Valid User user){
        User newUser = service.updateUserDetails(id, user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("updated user details successfully", newUser));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> closeAccount(@PathVariable long id){
         service.closeAccount(id);
         return ResponseEntity
                 .status(HttpStatus.NO_CONTENT)
                 .body( ApiResponse.error(204, "user account closed"));
    }
}
