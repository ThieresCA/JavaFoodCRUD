package com.example.cardapio.Domain.user;

import com.example.cardapio.Domain.Model.Entity.User;

public record UserRequestDto(String Name, String Email, String Senha) {
    public UserRequestDto(User user) {
        this(user.getEmail(), user.getPassword(), user.getName());
    }
}
