package com.account.repository;

import com.account.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<User, Long>{
}
