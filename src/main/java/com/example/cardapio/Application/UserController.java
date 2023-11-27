package com.example.cardapio.Application;

import com.example.cardapio.Domain.Model.Entity.User;


import com.example.cardapio.Domain.user.UserRequestDto;
import com.example.cardapio.Domain.user.UserResponseDto;
import com.example.cardapio.Interactor.UserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;

import java.util.List;


//dizendo que essa é uma classe de controller
@RestController
//@Slf4j
//mapeando qual o endpoint que vai chamar esse controller
@RequestMapping(value = "/user")
@Tag(name = "User-Controller")
public class UserController {

    @Autowired
    private UserUseCase repository;


    @GetMapping()
    @Operation(summary = "Realiza a consulta de todas os usuarios", method = "GET")
    public List<UserResponseDto> getAll() {
        List<UserResponseDto> userList = repository.GetAllUsers();
        return userList;
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "realiza a consulta de usuarios com o determinado email")
    public List getByEmail(@PathVariable String email){
        return repository.SearchByEmail(email);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Realiza a consulta especifica de um usuario com o determina ID", method = "GET")
    public List<UserResponseDto> getById(@PathVariable Long id) {
        List<UserResponseDto> user = repository.GetById(id);
        return user;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Realiza a criação do Usuário", method = "POST")
    public HttpStatusCode insertUser(@RequestBody UserRequestDto user){
        boolean response = repository.SaveUser(user);
        if (response) {
            return HttpStatusCode.valueOf(200);
        } else {
            throw new Error(String.valueOf(response));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Realiza a atualização do usuario", method = "PUT")
    public HttpStatusCode attFood(@RequestBody User user, @PathVariable Long id) {
        boolean response = repository.AttUser(user, id);
        if (response) {
            return HttpStatusCode.valueOf(200);
        } else {
            throw new Error(String.valueOf(response));
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Realiza a remoção do usuario especificado", method = "DELETE")
    public HttpStatusCode deleteUser(@PathVariable Long id) {
        boolean response = repository.DeleteUser(id);
        if (response) {
            return HttpStatusCode.valueOf(200);
        } else {
            throw new Error(String.valueOf(response));
        }
    }
}
