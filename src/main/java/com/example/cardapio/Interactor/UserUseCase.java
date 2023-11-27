package com.example.cardapio.Interactor;

import com.example.cardapio.Domain.Model.Entity.User;
import com.example.cardapio.Domain.user.UserRequestDto;
import com.example.cardapio.Domain.user.UserResponseDto;
import com.example.cardapio.Infrastructure.Repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.example.cardapio.Domain.Model.Services.HashCreator.createSHAHash;

@Service
public class UserUseCase {

    @Autowired
    private UserRepository _repository;


    public List<UserResponseDto> GetAllUsers() {
        List<User> response = _repository.findAll();
        List<UserResponseDto> list = response.stream().map(UserResponseDto::new).toList();
        return list;
    }

    public boolean SaveUser(@NotNull UserRequestDto user) {
        //criando o validador para garantir que os valores estão seguindo as regras da classe
        Validator validator;
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }

        User newUser = new User(null, user.Name(), user.Email(), user.Senha());
        Set<ConstraintViolation<User>> violations = validator.validate(newUser);

        if (violations.isEmpty()) {
            try {
                String hash = createSHAHash(user.Senha());
                newUser.setSenha(hash);
                _repository.save(newUser);
                return true;
            } catch (Exception e) {
                throw new Error(e);
            }
        } else {
            return false;
        }
    }

    public boolean DeleteUser(Long id) {
        if (id == null) {
            return false;
        }
        try {
            _repository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<UserResponseDto> GetById(Long id) {
        if (id == null) {
            throw new Error("não existe um usuário com esse Id");
        }
        List<User> response = Collections.singletonList(_repository.getById(id));
        List<UserResponseDto> list = response.stream().map(UserResponseDto::new).toList();
        return list;
    }

    public boolean AttUser(User user, Long id) {
        List<UserResponseDto> userList = GetById(id);
        if (userList.isEmpty()) {
            throw new Error("o usuario informado não existe");
        } else {
            user.setId(id);
            _repository.save(user);
            return true;
        }
    }

    public List SearchByEmail (String email){

        //decodificador necessário para transofrmar a uri que foi passada para o charset utf-8 exemplo @ que vinha como %40
        String test = java.net.URLDecoder.decode(email, StandardCharsets.UTF_8);

        if(email.isEmpty()){
            throw new Error("preencha corretamente");
        }
        List<UserResponseDto> response = _repository.seachByEmail(test).stream().toList();

        if (response.isEmpty()){
            throw new Error("Nenhum usuário com esse email cadastrado");
        }
        else {
            return response;
        }
    }
}
