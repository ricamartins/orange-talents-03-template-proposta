package com.zup.microservice.card.apis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zup.microservice.proposal.entities.Proposal;

public class CardRequest {

	@JsonProperty("documento")
	final String document;
	@JsonProperty("nome")
	final String name;
	@JsonProperty("idProposta")
	final String proposalId;
	
	public CardRequest(Proposal proposal) {
		this.document = proposal.getDocument();
		this.name = proposal.getName();
		this.proposalId = proposal.getId().toString();
	}
}
