package com.example.cardapio.controller;

import com.example.cardapio.food.Food;
import com.example.cardapio.food.FoodRepository;
import com.example.cardapio.food.FoodResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//dizendo que essa é uma classe de controller
@RestController
//mapeando qual o endpoint que vai chamar esse controller
@RequestMapping("/food")
public class FoodController {

    //injetando dependência
    @Autowired
    private FoodRepository repository;

    //indicando que quando vier o endpoint "food" com o método GET esse será o método acionado
    @GetMapping()
    public List<FoodResponseDto> getAll() {
        List<FoodResponseDto> foodList = repository.findAll().stream().map(FoodResponseDto::new).toList();
        return foodList;
    }
}
