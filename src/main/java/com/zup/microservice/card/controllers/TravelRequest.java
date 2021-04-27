package com.zup.microservice.card.controllers;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zup.microservice.card.apis.TravelNoticeRequest;
import com.zup.microservice.card.entities.Travel;

public class TravelRequest {

	@NotBlank @JsonProperty
	private String destination;
	
	@NotNull @Future @JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate endDate;

	@JsonCreator
	public TravelRequest(String destination) {
		this.destination = destination;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public Travel toEntity(String ipAddress, String userAgent) {
		return new Travel(destination, endDate, ipAddress, userAgent);
	}

	public TravelNoticeRequest toFeignRequest() {
		return new TravelNoticeRequest(destination, endDate);
	}
	
}
