package com.hotelbooking.exception;


import com.hotelbooking.enums.ResponseCodeEnum;
import com.hotelbooking.utils.ResponseObject;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HotelBookingExceptionHandler {

    @ExceptionHandler(value = { Exception.class})
    public ResponseObject handleAnyException(Exception ex) {

        String errorMessageDescription = ex.getLocalizedMessage();

        if (errorMessageDescription == null)
            errorMessageDescription = ex.toString();

        ResponseObject response = new ResponseObject();
        response.setCode(ResponseCodeEnum.INTERNAL_SERVER_ERROR.getCode());
        response.setMessage(errorMessageDescription);

        return response;

    }


}
