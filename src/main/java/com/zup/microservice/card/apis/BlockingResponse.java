package com.zup.microservice.card.apis;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BlockingResponse {

	@JsonProperty("bloqueadoEm")
	final LocalDateTime blockedAt;
	
	@JsonProperty("ativo")
	final Boolean active;

	public BlockingResponse(LocalDateTime blockedAt, Boolean active) {
		this.blockedAt = blockedAt;
		this.active = active;
	}
	
	
}
