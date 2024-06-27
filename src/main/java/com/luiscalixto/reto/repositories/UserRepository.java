package com.luiscalixto.reto.repositories;


import com.luiscalixto.reto.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,String> {
    Optional<User> findByEmail(String email);
}
