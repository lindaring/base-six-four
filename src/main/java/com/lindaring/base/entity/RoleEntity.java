package com.lindaring.base.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="roles")
public class RoleEntity extends BaseEntity {
    @Column(name="role")
    private String desc;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private List<UserEntity> users = new ArrayList<>();
}
