package com.example.ecommerce.exceptions;

import com.example.ecommerce.modules.address.exception.AddressException;
import com.example.ecommerce.modules.category.exceptions.CategoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.smartcardio.CardException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AddressException.class)
    public ResponseEntity<?> exceptionsAddress(AddressException e){
        ResponseError response = new ResponseError(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(CardException.class)
    public ResponseEntity<?> exceptionsCategory(CategoryException e){
        ResponseError response = new ResponseError(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionAll(Exception e){ // método coringa para tratamento de erros
        ResponseError response = new ResponseError(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
