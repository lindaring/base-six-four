package com.lindaring.base.enums;

import java.util.Arrays;
import java.util.Optional;

public enum RoleType {
    USER("USER", "ROLE_USER"),
    ADMIN("ADMIN", "ROLE_ADMIN");

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

    public static Optional<RoleType> getType(String shortDescription) {
        return Arrays.stream(values())
                .filter(rt -> rt.getShortDescription().equalsIgnoreCase(shortDescription))
                .findFirst();
    }
}
