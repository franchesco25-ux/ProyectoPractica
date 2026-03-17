package com.company.empresa.dto;

import com.company.empresa.entity.Role;
import com.company.empresa.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class LoginDTO {

    private String username;
    private String password;

}
