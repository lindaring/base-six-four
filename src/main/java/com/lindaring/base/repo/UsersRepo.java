package com.lindaring.base.repo;

import com.lindaring.base.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepo extends CrudRepository<User, Long> {
}
