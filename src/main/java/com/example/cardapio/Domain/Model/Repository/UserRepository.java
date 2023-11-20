package com.example.cardapio.Domain.Model.Repository;

import com.example.cardapio.Domain.Model.Entity.User;
import jakarta.websocket.RemoteEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}