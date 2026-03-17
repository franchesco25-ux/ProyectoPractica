package com.company.empresa.mapper;

import com.company.empresa.dto.RegisterDTO;
import com.company.empresa.entity.User;

public class Mapper {

    public static User toUser(RegisterDTO dto, String encodedPassword){
        return User.builder()
                .username(dto.getUsername())
                .password(encodedPassword)
                .build();
    }

}
