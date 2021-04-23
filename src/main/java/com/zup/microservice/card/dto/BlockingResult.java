package com.zup.microservice.card.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BlockingResult {

	@JsonProperty("resultado")
	final String result;

	@JsonCreator
	public BlockingResult(String result) {
		this.result = result;
	}

	public boolean isBlocked() {
		return result.equals("BLOQUEADO");
	}
	
}
