package com.lindaring.base.repo;

import com.lindaring.base.entity.ActivationCodeEntity;
import org.springframework.data.repository.CrudRepository;

public interface ActivationCodesRepo extends CrudRepository<ActivationCodeEntity, Long> {
}
