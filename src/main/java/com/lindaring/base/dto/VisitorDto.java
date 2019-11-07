package com.lindaring.base.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VisitorDto {
  @JsonProperty("id")
  private long id;

  @JsonProperty("ip")
  private String ip;

  @JsonProperty("insertDate")
  private Date insertDate;

  @JsonProperty("browser")
  private String browser;

  @JsonProperty("url")
  private String url;

  @JsonProperty("location")
  private String location;
}
