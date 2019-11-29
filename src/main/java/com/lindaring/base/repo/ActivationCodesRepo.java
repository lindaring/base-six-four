package com.lindaring.base.repo;

import com.lindaring.base.entity.ActivationCodeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ActivationCodesRepo extends CrudRepository<ActivationCodeEntity, Long> {
    Optional<ActivationCodeEntity> getActivationCodeByCode(String code);
}
