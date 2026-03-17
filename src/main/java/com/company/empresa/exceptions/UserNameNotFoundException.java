package com.company.empresa.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNameNotFoundException extends Exception{

    private ErrorResponse errorResponse;

    public UserNameNotFoundException(String message, ErrorResponse errorResponse){
        super(message);
        setErrorResponse(errorResponse);
    }

    public UserNameNotFoundException(String message, Throwable e){
        super(message, e);
    }

}
