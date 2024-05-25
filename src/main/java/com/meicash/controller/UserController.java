package com.meicash.controller;
import com.meicash.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController (final UserService receivedUserService) {
        this.userService = receivedUserService;
    }

    @GetMapping()
    public String getAllUsers() {
        return "Eu retorno todos os usuários!";
    }
    erro aqui

}
