package com.withdraw.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserAccount {
    private long userId;
    private String userName;
    private double balance;
}
