package com.bookstore.user.exception;

import org.springframework.http.HttpStatus;
/*****************************************************************************************************
 * Exception Class For UserAuthentication Failure Which extends RuntimeExcpetion
 *  
 * @author Rupesh Patil
 * @version 1.0
 * @created 2020-04-11
 *
 ******************************************************************************************************/

public class AuthenticationFailedException extends RuntimeException {
	 private static final long serialVersionUID = 1L;
	 private final HttpStatus httpStatus;

	public AuthenticationFailedException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}
	public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
