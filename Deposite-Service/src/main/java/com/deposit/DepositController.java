package com.deposit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/deposit")
public class DepositController {

    @Autowired
    private  RestTemplate restTemplate;

    @PostMapping("/add")
    public ResponseEntity<String> addBalance(@RequestBody TransactionRequest request){
        /*Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        //get the user
        UserAccount receiver =  restTemplate.getForObject("http://localhost:8080/user/"+request.getReceiver(), UserAccount.class);
        if (receiver == null){
            response.put("status", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        double updatedBalance = receiver.getBalance() + request.getAmount();
        restTemplate.put("http://localhost:8080/user/"+receiver.getUserId()+"/balance", updatedBalance);
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Amount Transferred in "+ receiver.getUserName()+" account");
        return new ResponseEntity<>(response, HttpStatus.OK);*/

        UserAccount receiver =  restTemplate.getForObject("http://localhost:8080/user/"+request.getReceiver(), UserAccount.class);
        if (receiver == null){

            return ResponseEntity.notFound().build();
        }
        double updatedBalance = receiver.getBalance() + request.getAmount();
        restTemplate.put("http://localhost:8080/user/"+receiver.getUserId()+"/balance", updatedBalance);
        return ResponseEntity.ok("Amount Transferred in " + receiver.getUserName() + " account");
    }

}
