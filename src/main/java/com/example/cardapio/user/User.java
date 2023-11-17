package com.example.cardapio.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
//dando um nome para a nossa entity
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long Id;
    public String Name;
    @Email(message = "Email should be valid")
    public String Email;
    public String Senha;
}

