package com.withdraw;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionRequest {
    private long sender;
    private long receiver;
    private double amount;
}
