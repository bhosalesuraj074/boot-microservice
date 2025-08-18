package com.account.services;

import com.account.dao.User;
import com.account.exceptions.ResourceNotFoundException;
import com.account.repository.AccountRepository;
import com.account.utility.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;

    // Cache the full list
    @Cacheable(value = "users")
    public List<User> loadAllUsers() {
        return repository.findAll();
    }

    // Cache by user ID
    @Cacheable(value = "user", key = "#id")
    public User loadUserById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id " + id));
    }

    // Save new user and put in cache with new id
    @CachePut(value = "user", key = "#result.id")
    @CacheEvict(value = {"user", "users"}, allEntries = true)
    public User save(User user) {
        return repository.save(user);
    }

    // Update existing user and refresh cache
    @CachePut(value = "user", key = "#id")
    @CacheEvict(value = {"user", "users"}, allEntries = true)
    public User updateUserDetails(long id, User user) {
        User oldUser = loadUserById(id);
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPhoneNumber(user.getPhoneNumber());
        oldUser.setDateOfBirth(user.getDateOfBirth());
        oldUser.setAddress(user.getAddress());
        return repository.save(oldUser);
    }

    // Remove from cache when deleted
    @CacheEvict(value = {"user", "users"}, allEntries = true)
    public void closeAccount(long id) {
        repository.deleteById(id);
    }
}

