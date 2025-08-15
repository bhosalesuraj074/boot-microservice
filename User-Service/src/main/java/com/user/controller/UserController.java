package com.user.controller;

import com.user.services.UserService;
import com.user.dao.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/user"})
public class UserController {
    @Autowired
    private UserService service;


    // get all the users
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUser(){
        List<UserAccount> userAccounts = service.loadAllUser();
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        if (!userAccounts.isEmpty()){
            response.put("data", userAccounts);
            response.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("message", "No user Found");
        response.put("status", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    // get user by there id
    @GetMapping("/{id}")
    public ResponseEntity<UserAccount> getUserById(@PathVariable long id){
        UserAccount userAccount = service.findUserById(id);
        return userAccount != null ? ResponseEntity.ok(userAccount) : ResponseEntity.notFound().build();
    }

    // get user by there name
    @GetMapping("/name/{username}")
    public ResponseEntity<Map<String, Object>> findUserByName(@PathVariable String username){
        UserAccount userAccount = service.loadUserByName(username);
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("message", "user found");
        response.put("status", HttpStatus.OK.value());
        response.put("data", userAccount);
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    // updating the balance into the user account
    @PutMapping("/{id}/balance")
    public ResponseEntity<String> updateBalance(@PathVariable long id, @RequestBody double balance){
             UserAccount userAccount = service.findUserById(id);
             userAccount.setBalance(balance);
             service.updateUser(userAccount);
             return ResponseEntity.ok("Balance Transferred successfully...");
    }




}
