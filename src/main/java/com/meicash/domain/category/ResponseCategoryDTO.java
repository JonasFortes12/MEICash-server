package com.meicash.domain.category;

public record ResponseCategoryDTO(
        String id,
        String name,
        String color
) {

    public ResponseCategoryDTO fromCategory(final Category category) {
        return new ResponseCategoryDTO(
                category.getId(),
                category.getName(),
                category.getColor()
        );
    }
}
