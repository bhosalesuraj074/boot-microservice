package com.withdraw;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/withdraw")
public class WithdrawController {

@Autowired
private FeignClientUserProxy feignClientUserProxy;
@Autowired
private FeignClientDepositProxy feignClientDepositProxy;

    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendMoney(@RequestBody TransactionRequest transactionRequest) {
        UserAccount sender = feignClientUserProxy.getUserById(transactionRequest.getSender());
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        // check user is exists or not
        if (sender == null) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Invalid Sender");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        // check send amount > account balance --> BAD_REQUEST
        if (sender.getBalance() < transactionRequest.getAmount()) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Insufficient balance");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        // Now deduct the amount from the sender account
        feignClientUserProxy.updateBalance(transactionRequest.getSender(), sender.getBalance() - transactionRequest.getAmount());
        // calling the Deposit service and deposit the amount to receiver account
        feignClientDepositProxy.addBalance(transactionRequest);
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Transaction successful from " + sender.getUserName() + " account");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




  /*  @Autowired
    private RestTemplate restTemplate;*/

   /* @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendMoney(@RequestBody TransactionRequest transactionRequest){
      UserAccount sender = restTemplate.getForObject("http://localhost:8080/user/"+transactionRequest.getSender(), UserAccount.class);
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        // check user is exits or not
      if (sender ==null ){
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", "Invalid Sender");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
      }

      // check send amount > account balance --> BAD_REQUEST
      if (sender.getBalance() <transactionRequest.getAmount()){
          response.put("status", HttpStatus.BAD_REQUEST.value());
          response.put("message", "Insufficient balance");
          return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
      }
      // Now deduct the amount from the sender account
        restTemplate.put("http://localhost:8080/user/"+transactionRequest.getSender()+"/balance", (sender.getBalance() - transactionRequest.getAmount()));

      // calling the Deposit service and deposit the amount to receiver account
        restTemplate.postForEntity("http://localhost:8082/deposit/add", transactionRequest, String.class);

        response.put("status", HttpStatus.OK.value());
        response.put("message", "Transaction successful from "+ sender.getUserName() + " account" );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    */

    }
