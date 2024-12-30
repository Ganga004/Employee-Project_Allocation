package com.project.allocation.exception;

import lombok.Data;

@Data
public class ProjectCustomException extends RuntimeException{
    public String errorCode;

    public ProjectCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
