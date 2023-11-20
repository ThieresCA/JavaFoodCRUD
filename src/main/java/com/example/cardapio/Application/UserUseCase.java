package com.example.cardapio.Application;

import com.example.cardapio.Domain.Model.Entity.User;
import com.example.cardapio.Domain.user.UserRequestDto;
import com.example.cardapio.Domain.user.UserResponseDto;
import com.example.cardapio.Infrastructure.Persistence.UserRepository;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import jakarta.validation.*;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

import static com.example.cardapio.Domain.Model.Services.HashCreator.createSHAHash;

public class UserUseCase {

    @Autowired
    private UserRepository repository;

    public List<UserResponseDto> GetAllUsers() {
        List<User> response = repository.GetAll();
        List<UserResponseDto> list = response.stream().map(UserResponseDto::new).toList();
        return list;
    }

    public boolean saveUser(UserRequestDto user) throws NoSuchAlgorithmException {
        //criando o validador para garantir que os valores estão seguindo as regras da classe
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        User newUser = new User(null, user.Name(), user.Email(), user.Senha());
        Set<ConstraintViolation<User>> violations = validator.validate(newUser);

        if (violations.isEmpty()) {
            try {
                String hash = createSHAHash(user.Senha());
                newUser.setSenha(hash);
                boolean response = repository.saveUser(newUser);
                return response;
            } catch (Exception e) {
                throw e;
            }
        } else {
            return false;
        }
    }

    public boolean DeleteUser(Long id) {
        if (id == null) {
            return false;
        }
        boolean response = repository.DeleteUser(id);
        return response;
    }

    public List<UserResponseDto> GetById(Long id) {
        if (id == null) {
            throw new Error("não existe um usuário com esse Id");
        }
        List<User> response = repository.GetById(id);
        List<UserResponseDto> list = response.stream().map(UserResponseDto::new).toList();
        return list;
    }
}
