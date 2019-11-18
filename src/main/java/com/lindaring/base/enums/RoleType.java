package com.lindaring.base.enums;

public enum RoleType {
  NORMAL_USER("USER", "ROLE_USER"),
  ADMINISTRATOR("ADMIN", "ROLE_ADMIN");

  private String shortDescription;
  private String fullDescription;

  RoleType(String shortDescription, String fullDescription) {
    this.shortDescription = shortDescription;
    this.fullDescription = fullDescription;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public String getFullDescription() {
    return fullDescription;
  }
}
