package com.meicash.controller;

import com.meicash.domain.auth.RequestAuthUserDTO;
import com.meicash.domain.auth.RequestUserRegisterDTO;
import com.meicash.domain.auth.ResponseAuthUserDTO;
import com.meicash.domain.user.User;
import com.meicash.domain.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<ResponseAuthUserDTO> login(@RequestBody @Valid RequestAuthUserDTO user) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(user.username(), user.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok().build();

    }

    @PostMapping("/register")
    public ResponseEntity<ResponseAuthUserDTO> register(@RequestBody @Valid RequestUserRegisterDTO userToRegister) {
        if(this.userRepository.findByUsername(userToRegister.username()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encriptedPassword = new BCryptPasswordEncoder().encode(userToRegister.password());
        User newUser = new User(
                userToRegister.email(),
                userToRegister.username(),
                encriptedPassword,
                userToRegister.firstName(),
                userToRegister.lastName(),
                userToRegister.companyName()
        );

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

}
