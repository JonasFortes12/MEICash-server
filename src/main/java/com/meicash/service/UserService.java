package com.meicash.service;


import com.meicash.domain.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(final UserRepository receivedUserRepository) {
        this.userRepository = receivedUserRepository;
    }

}
