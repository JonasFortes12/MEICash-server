package com.meicash.controller;

import com.meicash.domain.auth.RequestAuthUserDTO;
import com.meicash.domain.auth.RequestUserRegisterDTO;
import com.meicash.domain.auth.ResponseAuthUserDTO;
import com.meicash.domain.user.User;
import com.meicash.domain.user.UserRepository;
import com.meicash.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autenticação", description = "Operações relacionadas a autenticação do usuário")
@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Login do usuário", description = "Realiza o login do usuário e retorna o token de autenticação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem sucedido"),
            @ApiResponse(responseCode = "403", description = "Usuário ou senha inválidos")
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseAuthUserDTO> login(@RequestBody @Valid RequestAuthUserDTO userToLogin) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(userToLogin.username(), userToLogin.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken( (User) auth.getPrincipal());

        return ResponseEntity.ok(new ResponseAuthUserDTO(token));

    }

    @Operation(summary = "Registra um usuário", description = "Cria um novo usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário criado"),
            @ApiResponse(responseCode = "400", description = "Nome de usuário (username) já usado")
    })
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
