package com.example.cardapio.controller;

import com.example.cardapio.food.Food;
import com.example.cardapio.food.FoodRepository;
import com.example.cardapio.food.FoodResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//dizendo que essa é uma classe de controller
@RestController
@Slf4j
//mapeando qual o endpoint que vai chamar esse controller
@RequestMapping(value = "/food", produces = {"application/json"})
@Tag(name = "Food-Controller")
public class FoodController {

    //injetando dependência
    @Autowired
    private FoodRepository repository;

    //indicando que quando vier o endpoint "food" com o método GET esse será o método acionado
    @GetMapping()
    @Operation(summary = "Realiza a consulta de todas as comidas", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A inserção foi realizada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao procurar pelos dados"),
    })
    public List<FoodResponseDto> getAll() {
        List<FoodResponseDto> foodList = repository.findAll().stream().map(FoodResponseDto::new).toList();
        return foodList;
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A inserção foi realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao procurar pelos dados"),
    })
    @Operation(summary = "Realiza a consulta especifica de uma comida com o determina ID", method = "GET")
    public List<FoodResponseDto> getById(@PathVariable Long id) {
        List<FoodResponseDto> food = repository.findById(id).stream().map(FoodResponseDto::new).toList();
        return food;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    //@Operation(summary = "Realiza as inserções das comidas", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A inserção foi realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a inserção dos dados"),
    })
    public HttpStatusCode insertFood(@RequestBody Food food) {
        try {
            repository.save(food);
            return HttpStatusCode.valueOf(200);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Realiza a atualização das comidas", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A atualização foi realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a atualização dos dados"),
    })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A remoção foi realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a remoção dos dados"),
    })
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
