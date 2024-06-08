package com.meicash.domain.category;

public record ResponseCategoryDTO(
        String id,
        String name,
        String description
) {

    public ResponseCategoryDTO fromCategory(final Category category) {
        return new ResponseCategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
