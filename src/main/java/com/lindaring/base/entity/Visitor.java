package com.lindaring.base.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    public Visitor(@JsonProperty("id") long id,
                   @JsonProperty("ip") String ip,
                   @JsonProperty("insertDate") Date insertDate,
                   @JsonProperty("browser") String browser,
                   @JsonProperty("url") String url,
                   @JsonProperty("location") String location) {
        this.id = id;
        this.ip = ip;
        this.insertDate = insertDate;
        this.browser = browser;
        this.url = url;
        this.location = location;
    }
}
