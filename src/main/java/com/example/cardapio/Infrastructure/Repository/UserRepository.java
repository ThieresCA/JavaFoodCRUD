package com.example.cardapio.Infrastructure.Repository;

import com.example.cardapio.Domain.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // "obj" é um apelido dado para a entity que está relacionada a tabela
    // ou seja, o nome da tabela é usars, mas o nome da minha entity é User e é pela entity que vou procurar
    @Query(value = "SELECT obj.Id, obj.Name, obj.Email FROM User obj WHERE obj.Email = LOWER(:value)")
    List seachByEmail(String value);


}