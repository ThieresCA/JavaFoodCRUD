package com.example.cardapio.user;

public record UserResponseDto(Long Id, String Name, String Email, String Senha) {
    public UserResponseDto(User user) {
        this(user.getId(), user.getEmail(), user.getSenha(), user.getName());
    }
}
