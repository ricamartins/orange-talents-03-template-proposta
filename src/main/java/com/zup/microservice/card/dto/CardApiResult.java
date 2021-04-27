package com.zup.microservice.card.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CardApiResult {

	@JsonProperty("resultado")
	final String result;
	
	@JsonCreator
	public CardApiResult(String result) {
		this.result = result;
	}

	public boolean isBlocked() {
		return result.equals("BLOQUEADO");
	}

	public boolean isCreated() {
		return result.equals("CRIADO");
	}
}
