package com.meicash.domain.category;

import jakarta.validation.constraints.NotBlank;

public record RequestCategoryDTO(

        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Color is required")
        String color
) {

}
