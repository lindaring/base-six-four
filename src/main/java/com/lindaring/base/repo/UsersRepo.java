package com.lindaring.base.repo;

import com.lindaring.base.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepo extends CrudRepository<User, Long> {
    Optional<User> getUserByUsername(String username);
}
