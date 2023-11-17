package com.example.cardapio.food;

public record FoodRequestDto(String title, String image, Double price) {
    public FoodRequestDto(Food food) {
        this(food.getTitle(), food.getImage(), food.getPrice());
    }
}