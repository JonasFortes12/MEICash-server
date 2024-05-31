package com.meicash.domain.user;

public record ResponseUserDTO (
        String id,
        String email,
        String username,
        String firstName,
        String lastName,
        String companyName
) {

    public static ResponseUserDTO fromUser(final User user) {
        return new ResponseUserDTO(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getCompanyName()
        );
    }

}
