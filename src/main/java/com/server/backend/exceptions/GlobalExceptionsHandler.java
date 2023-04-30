package com.server.backend.exceptions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.NonUniqueResultException;

import javax.mail.SendFailedException;
import javax.persistence.NoResultException;
import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionsHandler.class);

    @ExceptionHandler(value = NoResultException.class)
    public ResponseEntity<Object> handleInvalidInputException(NoResultException ex) {
        logger.error("No data was found for query: " + ex.getMessage());
        return new ResponseEntity<Object>("No data found for your request", HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = RequestRejectedException.class)
    public ResponseEntity<Object> handleRejectedRequestException(RequestRejectedException ex) {
        logger.error("NRequest was rejected from firewall : " + ex.getMessage());
        return new ResponseEntity<Object>("No data found for your request", HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = NonUniqueResultException.class)
    public ResponseEntity<Object> handleInvalidInputException(NonUniqueResultException ex) {
        logger.error("More than one results found for query: " + ex.getMessage());
        return new ResponseEntity<Object>("More than one item found for your request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DuplicateEntityException.class)
    public ResponseEntity<Object> handleDuplicatedEntityException(DuplicateEntityException ex) {
        logger.error("More than one results found for query: " + ex.getMessage());
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Object> handleInvalidInputException(EntityNotFoundException ex) {
        logger.error("No Info found for query: " + ex.getMessage());
        return ResponseEntity.status( HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(value = DisabledException.class)
    public ResponseEntity<Object> handleDisabledUserException(DisabledException ex) {
        logger.error("User is disabled: " + ex.getMessage());
        return ResponseEntity.status( HttpStatus.NOT_FOUND).body("No Such User Found");
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        logger.error("Attempted LogIn with incorrect credentials " + ex.getMessage());
        return ResponseEntity.status( HttpStatus.UNAUTHORIZED).body("Incorrect Email or Password");
    }

    @ExceptionHandler(value = InternalAuthenticationServiceException.class)
    public ResponseEntity<Object> handleAuthenticationException(InternalAuthenticationServiceException ex) {
        logger.error("Failed Authentication : " + ex.getMessage());
        return ResponseEntity.status( HttpStatus.UNAUTHORIZED).body("Authentication Failed");
    }

    @ExceptionHandler(value = RequestRejectedException.class)
    public ResponseEntity<Object> handleAuthenticationException(RequestRejectedException ex) {
        logger.error("Failed Authentication : " + ex.getMessage());
        return ResponseEntity.status( HttpStatus.UNAUTHORIZED).body("Authentication Failed");
    }

    @ExceptionHandler(value = IllegalAccessException.class)
    public ResponseEntity<Object> handleBadCredentialsException(IllegalAccessException ex) {
        logger.error("User trying to access restricted resources : " + ex.getMessage());
        return ResponseEntity.status( HttpStatus.BAD_REQUEST).body("No permission to access these resources");
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> test(Exception ex) {
        logger.error("General Exception :");
        ex.printStackTrace();
        return new ResponseEntity<Object>("Something Went Wrong! Support is looking into it.", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(value = MissingRequestHeaderException.class)
    protected ResponseEntity<Object> handleRequestHeaderException(MissingRequestHeaderException ex) {
        logger.error("Authentication Exception : " + ex.getMessage());
        return ResponseEntity.status( HttpStatus.BAD_REQUEST).body("Authentication Failed");
    }

    @ExceptionHandler(value = SendFailedException.class)
    protected ResponseEntity<Object> handleWrongMailException(SendFailedException exception) {
        logger.error("Invalid Email : " + exception.getMessage());
        return ResponseEntity.status( HttpStatus.BAD_REQUEST).body("Authentication Failed");
    }
}
