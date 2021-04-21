package com.zup.microservice.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zup.microservice.proposal.Proposal;

public class CardResponse {

	@JsonProperty
	final String id;
	
	@JsonCreator
	public CardResponse(String id) {
		this.id = id;
	}

	public Card toEntity(Proposal proposal) {
		return new Card(id, proposal);
	}

}
