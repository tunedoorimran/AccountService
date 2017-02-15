package com.tunedoor.microservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * 
 * @author Mohamed Saeed
 *
 */
@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class InsufficientBalanceException extends RuntimeException {

	private static final long serialVersionUID = -547866207509720048L;

	public InsufficientBalanceException(String message){
		super(message);
	}
}
