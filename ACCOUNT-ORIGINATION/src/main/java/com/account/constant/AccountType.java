package com.account.constant;

import lombok.Getter;

@Getter
public enum AccountType {
    SAVING("saving"),
    CURRENT("current"),
    SALARY("salary");
    private final String value;
    AccountType(String value) {
        this.value = value;
    }
}
