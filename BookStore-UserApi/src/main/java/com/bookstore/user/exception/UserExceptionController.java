package com.bookstore.user.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.bookstore.user.response.CustomErrorResponse;

/*****************************************************************************************************
 * Exception Controller Class For handle all the user exceptions.
 *  
 * @author Rupesh Patil
 * @version 1.0
 * @created 2020-04-11
 *
 ******************************************************************************************************/

@ControllerAdvice
public class UserExceptionController extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(AuthenticationFailedException.class)
	public ResponseEntity<Object> authenticationFailedException(Exception ex, WebRequest request) {
		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ex.getMessage());
		errors.setStatus(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidTokenOrExpiredException.class)
	public ResponseEntity<Object> invalidTokenOrExpiredException(Exception ex, WebRequest request) {
		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ex.getMessage());
		errors.setStatus(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(UserAlradyRegisterException.class)
	public ResponseEntity<Object> userAlradyRegisterException(Exception ex, WebRequest request) {
		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ex.getMessage());
		errors.setStatus(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}

}
