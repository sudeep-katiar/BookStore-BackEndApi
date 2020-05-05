package com.bookstore.user.exception;

import org.springframework.http.HttpStatus;

/*****************************************************************************************************
 * Exception Class For Token Exeption Like Token parse Error Or Token Expired Which extends RuntimeExcpetion
 *  
 * @author Rupesh Patil
 * @version 1.0
 * @created 2020-04-11
 *
 ******************************************************************************************************/

public class InvalidTokenOrExpiredException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final HttpStatus httpStatus;

	public InvalidTokenOrExpiredException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
