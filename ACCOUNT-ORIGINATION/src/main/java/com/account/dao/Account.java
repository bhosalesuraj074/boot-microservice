package com.account.dao;

import com.account.constant.AccountType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Account-Details")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_number", unique = true, updatable = false, nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private long aadhaarNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;
}
