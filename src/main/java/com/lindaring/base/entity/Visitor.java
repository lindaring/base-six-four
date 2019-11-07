package com.lindaring.base.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor
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
    private String url;
    private String location;
}
