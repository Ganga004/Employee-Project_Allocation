package com.employee.data.exception;

import lombok.Data;

@Data
public class EmployeeCustomException extends RuntimeException{
    public String errorCode;

    public EmployeeCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
