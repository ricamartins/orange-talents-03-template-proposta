package com.zup.microservice.card.apis;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zup.microservice.card.entities.Card;
import com.zup.microservice.card.entities.Card.CardStatus;
import com.zup.microservice.proposal.entities.Proposal;

public class CardResponse {

	@JsonProperty
	final String id;
	
	@JsonProperty("bloqueios")
	final List<BlockingResponse> blockings;
	
	public CardResponse(String id, List<BlockingResponse> blockings) {
		this.id = id;
		this.blockings = blockings;
	}

	public Card toEntity(CardStatus status, Proposal proposal) {
		return new Card(id, status, proposal);
	}

}
