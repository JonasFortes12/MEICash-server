package com.meicash.service;


import com.meicash.domain.user.ResponseUserDTO;
import com.meicash.domain.user.User;
import com.meicash.domain.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(final UserRepository receivedUserRepository) {
        this.userRepository = receivedUserRepository;
    }

    private ResponseUserDTO userToResponseUserDTO(User user) {
        return new ResponseUserDTO(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getCompanyName()
        );
    }

    public List<ResponseUserDTO> getAllUsers() {
        List<ResponseUserDTO> allUsers = new ArrayList<>();
        List<User> retrievedUsers = userRepository.findAll();

        for (User retrievedUser : retrievedUsers) {
            allUsers.add(userToResponseUserDTO(retrievedUser));
        }
        return allUsers;
    }


}
