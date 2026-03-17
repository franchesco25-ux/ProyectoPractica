package com.company.empresa.dto;

import com.company.empresa.entity.ERole;
import com.company.empresa.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RegisterDTO {

    private String username;
    private String password;

}
