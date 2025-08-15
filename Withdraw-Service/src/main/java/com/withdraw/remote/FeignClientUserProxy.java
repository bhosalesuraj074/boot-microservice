package com.withdraw.remote;

import com.withdraw.dto.UserAccount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "USER-SERVICE", url = "${user.service.url}")
public interface FeignClientUserProxy {
    @GetMapping("/{id}")
    UserAccount getUserById(@PathVariable("id") long id);

    @PutMapping("/{id}/balance")
    String updateBalance(@PathVariable("id") long id, @RequestBody double balance);
}
