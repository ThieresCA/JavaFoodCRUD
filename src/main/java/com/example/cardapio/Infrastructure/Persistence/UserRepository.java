package com.example.cardapio.Infrastructure.Persistence;

import com.example.cardapio.Domain.Model.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserRepository {

    @Autowired
    private com.example.cardapio.Domain.Model.Repository.UserRepository repository;

    public boolean saveUser(User user) {
        try {
            repository.save(user);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<User> GetAll() {
        List<User> userList = repository.findAll().stream().toList();
        return userList;
    }

    public boolean DeleteUser(Long id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e){
            throw e;
        }
    }

    public List<User> GetById(Long id){
        List<User> list = repository.findById(id).stream().toList();
        return list;
    }
}