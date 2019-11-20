package com.lindaring.base.repo;

import com.lindaring.base.entity.VisitorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface VisitorsRepo extends CrudRepository<VisitorEntity, Long> {

    @Query(value = "SELECT v FROM VisitorEntity v WHERE insertDate >= :startDate AND insertDate <= :endDate")
    List<VisitorEntity> findByInsertDate(@Param("startDate") Date startDate,
                                         @Param("endDate") Date endDate);

    @Query(value = "SELECT DISTINCT v.ip FROM VisitorEntity v WHERE insertDate >= :startDate AND insertDate <= :endDate")
    List<String> findDistinctIpByInsertDate(@Param("startDate") Date startDate,
                                            @Param("endDate") Date endDate);
}
