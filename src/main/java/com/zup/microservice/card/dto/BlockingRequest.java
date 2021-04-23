package com.zup.microservice.card.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BlockingRequest {

	@JsonProperty("sistemaResponsavel")
	final String service;

	@JsonCreator
	public BlockingRequest(String service) {
		this.service = service;
	}
	
}
