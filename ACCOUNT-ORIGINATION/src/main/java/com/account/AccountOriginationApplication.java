package com.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AccountOriginationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountOriginationApplication.class, args);
	}

}
