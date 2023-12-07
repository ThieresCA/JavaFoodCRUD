package com.example.cardapio.Domain.user;

import com.example.cardapio.Domain.Model.Entity.User;

public record UserResponseDto(Long Id, String Name, String Email, String Senha) {
    public UserResponseDto(User user) {
        this(user.getId(), user.getEmail(), user.getPassword(), user.getName());
    }
}
