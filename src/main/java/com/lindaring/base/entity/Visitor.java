package com.lindaring.base.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name="visitors")
public class Visitor {
    @Id
    //@GeneratedValue
    private long id;
    private String ip;
    @Column(name="insertDate")
    private Date insertDate;
    private String browser;
}
