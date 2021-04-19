package com.zup.microservice.validations;

import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility=Visibility.ANY)
public class ResponseError {

	private String field;
	private String message;
	
	public ResponseError(FieldError fieldError) {
		this.field = fieldError.getField();
		this.message = fieldError.getDefaultMessage();
	}

	public ResponseError(String fieldError) {
		String[] errorPair = fieldError.split(":");
		this.field = errorPair[0];
		this.message = errorPair[1];
	}
	
}
