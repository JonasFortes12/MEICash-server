package com.meicash.controller;
import com.meicash.domain.user.RequestUserDTO;
import com.meicash.domain.user.ResponseUserDTO;
import com.meicash.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController (final UserService receivedUserService) {
        this.userService = receivedUserService;
    }

    @GetMapping()
    public List<ResponseUserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping()
    public ResponseEntity<ResponseUserDTO> createUser(@RequestBody @Valid final RequestUserDTO requestUserDTO) {
        ResponseUserDTO createdUser = userService.createUser(requestUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
