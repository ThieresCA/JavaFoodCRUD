package com.example.cardapio.food;

public record FoodResponseDto(Long id, String title, String image, Integer price) {
    public FoodResponseDto(Food food) {
        this(food.getId(), food.getTitle(), food.getImage(), food.getPrice());
    }
}
