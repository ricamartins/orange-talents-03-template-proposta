package com.zup.microservice.card.apis;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DigitalWalletFeignRequest {

	@JsonProperty
	final String email;
	
	@JsonProperty("carteira")
	final String digitalWalletService;

	public DigitalWalletFeignRequest(@NotBlank @Email String email, @NotBlank String digitalWalletService) {
		this.email = email;
		this.digitalWalletService = digitalWalletService;
	}

}
