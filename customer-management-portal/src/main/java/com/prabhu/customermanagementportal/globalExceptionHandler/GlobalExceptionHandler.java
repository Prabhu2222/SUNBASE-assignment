package com.prabhu.customermanagementportal.globalExceptionHandler;

import com.prabhu.customermanagementportal.exception.EmailAlreadyExistException;
import com.prabhu.customermanagementportal.exception.EmailUpdationException;
import com.prabhu.customermanagementportal.responseModel.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ResponseMessage> handleEmailAlreadyExistsException(EmailAlreadyExistException ex){
        ResponseMessage responseMessage=ResponseMessage.builder()
                .message(ex.getMessage())
                .error(true)
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailUpdationException.class)
    public ResponseEntity<ResponseMessage> handleEmailUpdationException(EmailUpdationException ex){
        ResponseMessage responseMessage=ResponseMessage.builder()
                .error(true)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.BAD_REQUEST);
    }
}
