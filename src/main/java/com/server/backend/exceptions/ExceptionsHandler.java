package com.server.backend.exceptions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.hibernate.NonUniqueResultException;


import javax.persistence.NoResultException;

@RestControllerAdvice("com.plantapp.server.controllers")
public class ExceptionsHandler {

    private static final Logger logger = LoggerFactory.getLogger(com.server.backend.exceptions.ExceptionsHandler.class);

    @ExceptionHandler(value = {NoResultException.class})
    public ResponseEntity<Object> handleInvalidInputException(NoResultException ex) {
        logger.error("No info for query found: ", ex.getMessage());
        return new ResponseEntity<Object>("No data found for your request", HttpStatus.NO_CONTENT);

    }

    @ExceptionHandler(value = {NonUniqueResultException.class})
    public ResponseEntity<Object> handleInvalidInputException(NonUniqueResultException ex) {
        logger.error("More than one results found for query: ", ex.getMessage());
        return new ResponseEntity<Object>("More than one item found for your request", HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleInvalidInputException(EntityNotFoundException ex) {
        logger.error("No Info found for query: ", ex.getMessage());
        return ResponseEntity.status( HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
