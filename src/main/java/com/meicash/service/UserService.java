package com.meicash.service;


import com.meicash.domain.user.RequestUserDTO;
import com.meicash.domain.user.ResponseUserDTO;
import com.meicash.domain.user.User;
import com.meicash.domain.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    public ResponseUserDTO createUser(final RequestUserDTO requestUserDTO) {
        User newUser = new User(requestUserDTO);
        return userToResponseUserDTO(userRepository.save(newUser));
    }

    public Optional<ResponseUserDTO> getUserById(final String userId) {
        Optional<User> retrievedUser = userRepository.findById(userId);
        if (retrievedUser.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(userToResponseUserDTO(retrievedUser.get()));
    }

    public Optional<ResponseUserDTO> updateUser(String userId, RequestUserDTO requestUserDTO) {

        return userRepository.findById(userId)
                .map(user -> {
                    user.setEmail(requestUserDTO.email());
                    user.setUsername(requestUserDTO.username());
                    user.setPassword(requestUserDTO.password());
                    user.setFirstName(requestUserDTO.firstName());
                    user.setLastName(requestUserDTO.lastName());
                    user.setCompanyName(requestUserDTO.companyName());
                    return userToResponseUserDTO(userRepository.save(user));
                });
    }

    public boolean deleteUser(final String userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
