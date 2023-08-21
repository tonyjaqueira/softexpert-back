package com.softexpertpayments.domain.exception;

import com.softexpertpayments.domain.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = ExceptionGeneralCustomer.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleCustomerLoginException(ExceptionGeneralCustomer ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()) {
        };
    }

}
