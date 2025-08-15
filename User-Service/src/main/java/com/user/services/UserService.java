package com.user.services;

import com.user.repositories.UserRepository;
import com.user.dao.UserAccount;
import com.user.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // get user by there id
    @Transactional
    public UserAccount findUserById(long userId){
         return  userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User with ID " + userId + " not found"));
    }

    //get user by there name

    @Transactional
    public UserAccount loadUserByName(String name){
      return    userRepository.findByUserNameIgnoreCase(name).orElseThrow(()-> new UserNotFoundException("User not found with name:" + name));
    }

    //load all the users form the database
    @Transactional
    public List<UserAccount> loadAllUser(){
        return userRepository.findAll();
    }

    //get the saving the student info
    @Transactional
    public UserAccount updateUser(UserAccount userAccount){
        return  userRepository.save(userAccount);
    }


}
