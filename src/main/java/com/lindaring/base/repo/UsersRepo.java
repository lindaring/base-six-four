package com.lindaring.base.repo;

import com.lindaring.base.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepo extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> getUserByUsername(String username);
}
