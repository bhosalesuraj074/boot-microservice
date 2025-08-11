package com.deposit;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAccount {
    private long userId;
    private String userName;
    private double balance;
}
