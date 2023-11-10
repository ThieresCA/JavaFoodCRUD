package com.example.cardapio.food;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

//indica o nome da tabela dentro do banco
@Table(name = "foods")
//dando um nome para a nossa entity
@Entity(name = "foods")
//adicionando os getter das propriedades
@Getter
//criando construtor com todas os argumentos
@AllArgsConstructor
//criando o construtor sem nenhum argumento
@NoArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String image;
    private Integer price;
}
