package com.meicash.controller;
import com.meicash.domain.user.RequestUserDTO;
import com.meicash.domain.user.ResponseUserDTO;
import com.meicash.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseUserDTO> getUserById(@PathVariable final String userId) {
        Optional<ResponseUserDTO> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @PostMapping()
    public ResponseEntity<ResponseUserDTO> createUser(@RequestBody @Valid final RequestUserDTO requestUserDTO) {
        ResponseUserDTO createdUser = userService.createUser(requestUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ResponseUserDTO> updateUser(@PathVariable final String userId, @RequestBody @Valid final RequestUserDTO requestUserDTO) {
        Optional<ResponseUserDTO> updatedUser = userService.updateUser(userId, requestUserDTO);
        if (updatedUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser.get());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable final String userId) {
        if (userService.deleteUser(userId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
