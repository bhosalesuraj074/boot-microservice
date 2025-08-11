package com.withdraw;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "DEPOSIT-SERVICE", url = "${deposit.service.url}")
public interface FeignClientDepositProxy {
    @PostMapping("/add")
    String addBalance(@RequestBody TransactionRequest request);

}
