package com.example.cardapio.Domain.Model.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository  extends JpaRepository<Food, Long> {
}
