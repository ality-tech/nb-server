package org.neighbor.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.neighbor.api.ErrorCode;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.JsonError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralResponse> handleError(HttpServletRequest req, Exception ex) {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.GENERAL_SERVER_ERROR);
        error.setMessage(ex.getMessage());//todo remove before production!!!
        GeneralResponse body = new GeneralResponse(500, error);
        ResponseEntity<GeneralResponse> response = new ResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR);
        return response;
    }
}
