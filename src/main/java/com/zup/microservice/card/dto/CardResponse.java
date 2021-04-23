package com.zup.microservice.card.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zup.microservice.card.entities.Card;
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

	public Card toEntity(Proposal proposal) {
		return new Card(id, proposal);
	}

	public boolean isBlocked() {
		if (blockings.isEmpty()) return false;
		//assuming they are ordered by creation time
		return blockings.get(blockings.size() - 1).active;
	}

}
