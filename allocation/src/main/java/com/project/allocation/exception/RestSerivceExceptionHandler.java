package com.project.allocation.exception;

import com.project.allocation.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestSerivceExceptionHandler extends ResponseEntityExceptionHandler {

 @ExceptionHandler(ProjectCustomException.class)
 public ResponseEntity<ErrorResponse> handleProductServiceException(ProjectCustomException projectCustomException) {
     return new ResponseEntity<>(ErrorResponse.builder()
             .errorCode(projectCustomException.getErrorCode())
             .errorMessage(projectCustomException.getMessage()).
             build(), HttpStatus.NOT_FOUND);
 }
}
