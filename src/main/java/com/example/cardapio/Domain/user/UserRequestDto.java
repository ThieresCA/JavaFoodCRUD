package com.example.cardapio.Domain.user;

public record UserRequestDto(String Name, String Email, String Senha) {
    public UserRequestDto(User user) {
        this(user.getEmail(), user.getSenha(), user.getName());
    }
}
