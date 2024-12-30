package com.employee.data.exception;

import com.employee.data.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestSerivceExceptionHandler extends ResponseEntityExceptionHandler {

 @ExceptionHandler(EmployeeCustomException.class)
 public ResponseEntity<ErrorResponse> handleProductServiceException(EmployeeCustomException employeeCustomException) {
     return new ResponseEntity<>(ErrorResponse.builder()
             .errorCode(employeeCustomException.getErrorCode())
             .errorMessage(employeeCustomException.getMessage()).
             build(), HttpStatus.NOT_FOUND);
 }
}
