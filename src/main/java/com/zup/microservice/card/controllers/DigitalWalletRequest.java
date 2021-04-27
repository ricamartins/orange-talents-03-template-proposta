package com.zup.microservice.card.controllers;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.zup.microservice.card.apis.DigitalWalletFeignRequest;
import com.zup.microservice.card.entities.DigitalWallet;
import com.zup.microservice.card.entities.DigitalWallet.DigitalWalletService;
import com.zup.microservice.validations.ValidEnum;

public class DigitalWalletRequest {

	@NotBlank @Email
	final String email;
	
	@NotBlank @ValidEnum(DigitalWalletService.class)
	final String digitalWalletService;

	public DigitalWalletRequest(@NotBlank @Email String email, @NotBlank String digitalWalletService) {
		this.email = email;
		this.digitalWalletService = digitalWalletService;
	}

	public DigitalWalletFeignRequest toFeignRequest() {
		return new DigitalWalletFeignRequest(email, digitalWalletService);
	}

	public DigitalWallet toEntity(String id) {
		return new DigitalWallet(id, email, DigitalWalletService.valueOf(digitalWalletService));
	}
	
	
}
