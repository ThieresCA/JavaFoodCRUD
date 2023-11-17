package com.example.cardapio.user;

import com.example.cardapio.food.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
}