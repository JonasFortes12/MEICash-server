package com.meicash.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.meicash.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private static final String TOKEN_ISSUER = "meicash-server";

    public String generateToken(User user) {
        try {
            String token;
            Algorithm algorithm = Algorithm.HMAC512(this.secret);
            token = JWT.create()
                    .withIssuer(TOKEN_ISSUER)
                    .withSubject(user.getUsername())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

            return token;

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error creating token", exception);
        }
    }


    public String getUsernameFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(this.secret);
            return JWT.require(algorithm)
                    .withIssuer(TOKEN_ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            System.out.println("Error verifying token !!!!");
           return "";
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("-03:00"));
    }

}