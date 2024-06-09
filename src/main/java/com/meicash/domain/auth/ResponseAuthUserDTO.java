package com.meicash.domain.auth;

public record ResponseAuthUserDTO(
        String token,
//        int expiresIn,
        String tokenType
) {
}
