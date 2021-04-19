package com.zup.microservice.validations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ValidationsControllerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public List<ResponseError> handle(MethodArgumentNotValidException exception) {
		return exception.getBindingResult().getFieldErrors().stream()
				.map(ResponseError::new).collect(Collectors.toList());
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ResponseError> handle(ResponseStatusException exception) {
		return ResponseEntity.status(exception.getStatus())
				.body(new ResponseError(exception.getReason()));
	}
}
