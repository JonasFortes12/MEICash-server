package com.meicash.domain.category;

public record ResponseCategoryDTO(
        String name,
        String description
) {

    public RequestCategoryDTO fromCategory(final Category category) {
        return new RequestCategoryDTO(
                category.getName(),
                category.getDescription()
        );
    }
}
