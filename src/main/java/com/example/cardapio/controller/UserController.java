package com.example.cardapio.controller;

import com.example.cardapio.services.HashCreator;
import com.example.cardapio.user.User;
import com.example.cardapio.user.UserRepository;
import com.example.cardapio.user.UserRequestDto;
import com.example.cardapio.user.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import static com.example.cardapio.services.HashCreator.createSHAHash;


//dizendo que essa é uma classe de controller
@RestController
//@Slf4j
//mapeando qual o endpoint que vai chamar esse controller
@RequestMapping(value = "/user")
@Tag(name = "User-Controller")
public class UserController {

    @Autowired
    private UserRepository repository;
    @GetMapping()
    @Operation(summary = "Realiza a consulta de todas os usuarios", method = "GET")
    public List<UserResponseDto> getAll() {
        List<UserResponseDto> userList = repository.findAll().stream().map(UserResponseDto::new).toList();
        return userList;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Realiza a consulta especifica de um usuario com o determina ID", method = "GET")
    public List<UserResponseDto> getById(@PathVariable Long id) {
        List<UserResponseDto> user = repository.findById(id).stream().map(UserResponseDto::new).toList();
        return user;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Realiza a criação do Usuário", method = "POST")
    public HttpStatusCode insertUser(@RequestBody UserRequestDto user) throws NoSuchAlgorithmException {
        try {
            String hash = createSHAHash(user.Senha());
            User newUser = new User (null, user.Name(), user.Email(), hash);
            repository.save(newUser);
            return HttpStatusCode.valueOf(200);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Realiza a atualização do usuario", method = "PUT")
    public HttpStatusCode attFood(@RequestBody User user, @PathVariable Long id) {
        Optional userList = repository.findById(id);
        if (userList.isEmpty()) {
            throw new Error("o item que está procurando não existe");
        } else {
            user.setId(id);
            repository.save(user);
            return HttpStatusCode.valueOf(200);
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Realiza a remoção do usuario especificado", method = "DELETE")
    public HttpStatusCode deleteUser(@PathVariable Long id) {
        List<User> userList = repository.findById(id).stream().toList();
        if (userList.isEmpty()) {
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
