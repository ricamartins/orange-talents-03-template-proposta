package com.zup.microservice.card.apis;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TravelNoticeRequest {

	@JsonProperty("destino")
	final String destination;

	@JsonProperty("validoAte")
	final LocalDate endDate;

	public TravelNoticeRequest(String destination, LocalDate endDate) {
		this.destination = destination;
		this.endDate = endDate;
	}
	
}
