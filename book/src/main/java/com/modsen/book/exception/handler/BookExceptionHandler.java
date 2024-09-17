package com.modsen.book.exception.handler;

import com.modsen.book.exception.NotCreatedException;
import com.modsen.book.exception.NotFoundException;
import com.modsen.book.exception.WebClientException;
import com.modsen.book.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookExceptionHandler {

    @ExceptionHandler(value = {WebClientException.class})
    public ResponseEntity<Object> handleNotCreatedException(WebClientException webClientException) {
        ExceptionResponse response =
                new ExceptionResponse(HttpStatus.BAD_REQUEST,
                        webClientException.getMessage()
                );
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException) {
        ExceptionResponse response =
                new ExceptionResponse(HttpStatus.NOT_FOUND,
                        notFoundException.getMessage()
                );
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(value = {NotCreatedException.class})
    public ResponseEntity<Object> handleNotCreatedException(NotCreatedException notCreatedException) {
        ExceptionResponse response =
                new ExceptionResponse(HttpStatus.BAD_REQUEST,
                        notCreatedException.getMessage()
                );
        return new ResponseEntity<>(response, response.getStatus());
    }


}
