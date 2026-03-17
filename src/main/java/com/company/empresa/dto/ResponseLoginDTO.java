package com.company.empresa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseLoginDTO {

    private String username;
    private String token;
    private String refreshToken;
}