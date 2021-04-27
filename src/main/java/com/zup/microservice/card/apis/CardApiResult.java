package com.zup.microservice.card.apis;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CardApiResult {

	@JsonProperty("resultado")
	private String result;
	
	private String id;
	
	@JsonCreator
	public CardApiResult(String result, String id) {
		this.result = result;
		this.id = id;
	}
	
	public boolean isBlocked() {
		return result.equals("BLOQUEADO");
	}

	public boolean isCreated() {
		return result.equals("CRIADO");
	}

	public boolean isAssociated() {
		return result.equals("ASSOCIADA");
	}

	public String getId() {
		return id;
	}
}
