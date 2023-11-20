package com.example.cardapio.controller;

import com.example.cardapio.food.Food;
import com.example.cardapio.food.FoodRepository;
import com.example.cardapio.food.FoodRequestDto;
import com.example.cardapio.food.FoodResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//dizendo que essa é uma classe de controller
@RestController
//@Slf4j
//mapeando qual o endpoint que vai chamar esse controller
@RequestMapping(value = "/food")
@Tag(name = "Food-Controller")
public class FoodController {

    //injetando dependência
    @Autowired
    private FoodRepository repository;

    //indicando que quando vier o endpoint "food" com o método GET esse será o método acionado
    @GetMapping()
    @Operation(summary = "Realiza a consulta de todas as comidas", method = "GET")
    public List<FoodResponseDto> getAll() {
        List<FoodResponseDto> foodList = repository.findAll().stream().map(FoodResponseDto::new).toList();
        return foodList;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Realiza a consulta especifica de uma comida com o determina ID", method = "GET")
    public List<FoodResponseDto> getById(@PathVariable Long id) {
        List<FoodResponseDto> food = repository.findById(id).stream().map(FoodResponseDto::new).toList();
        return food;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Realiza a inserção da comida no banco de dados", method = "GET")
    public HttpStatusCode insertFood(@RequestBody FoodRequestDto food) {
        try {
            Food newFood = new Food (null, food.title(), food.image(), food.price());
            repository.save(newFood);
            return HttpStatusCode.valueOf(200);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Realiza a atualização das comidas", method = "PUT")
    public HttpStatusCode attFood(@RequestBody Food food, @PathVariable Long id) {
        Optional foodList = repository.findById(id);
        if (foodList.isEmpty()) {
            throw new Error("o item que está procurando não existe");
        } else {
            food.setId(id);
            repository.save(food);
            return HttpStatusCode.valueOf(200);
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Realiza a remoção da comida especificada", method = "DELETE")
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
