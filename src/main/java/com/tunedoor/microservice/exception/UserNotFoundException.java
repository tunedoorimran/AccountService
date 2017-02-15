package com.tunedoor.microservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * 
 * @author Mohamed Saeed
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -547866207509720048L;

	public UserNotFoundException(String message){
		super(message);
	}
}
