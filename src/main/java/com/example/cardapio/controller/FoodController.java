package com.example.cardapio.controller;

import com.example.cardapio.food.Food;
import com.example.cardapio.food.FoodRepository;
import com.example.cardapio.food.FoodResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public List<FoodResponseDto> getById(@PathVariable Long id) {
        List<FoodResponseDto> food = repository.findById(id).stream().map(FoodResponseDto::new).toList();
        return food;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatusCode insertFood(@RequestBody Food food) {
        try {
            repository.save(food);
            return HttpStatusCode.valueOf(200);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/{id}")
    public HttpStatusCode attFood(@RequestBody Food food, @PathVariable Long id) {
        var foodList = repository.findById(id);
        if (foodList.isEmpty()) {
            throw new Error("o item que está procurando não existe");
        } else {
            food.setId(id);
            repository.save(food);
            return HttpStatusCode.valueOf(200);
        }
    }

    @DeleteMapping("{id}")
    public HttpStatusCode deleteFood(@PathVariable Long id) {
        List<Food> foodList = repository.findById(id).stream().toList();
        if (foodList.isEmpty()) {
            throw new Error("o item que está procurando não existe");
        } else {
            try {
                repository.deleteById(id);
                return HttpStatusCode.valueOf(200);
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
