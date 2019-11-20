package com.lindaring.base.dto;

import com.lindaring.base.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisteredUser {
    private String username;
    private String password;
    private boolean activated;
    private List<RoleType> roles;
}
