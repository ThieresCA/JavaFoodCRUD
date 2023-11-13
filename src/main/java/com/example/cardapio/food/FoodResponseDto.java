package com.example.cardapio.food;

public record FoodResponseDto(Long id, String title, String image, Double price) {
    public FoodResponseDto(Food food) {
        this(food.getId(), food.getTitle(), food.getImage(), food.getPrice());
    }
}
