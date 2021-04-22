package com.zup.microservice.card;

import java.util.Base64;

import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BiometryRequest {

	@NotBlank @JsonProperty
	final String fingerprint;

	@JsonCreator
	public BiometryRequest(@NotBlank String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public Biometry convert() {
		try {
			Base64.getDecoder().decode(fingerprint);
			return new Biometry(fingerprint);		
		}
		catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fingerprint:A digital não está em formato Base64");
		}
	}
	
}
