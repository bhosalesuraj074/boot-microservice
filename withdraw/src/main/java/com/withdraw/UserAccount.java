package com.withdraw;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserAccount {
    private long userId;
    private String userName;
    private double balance;
}
