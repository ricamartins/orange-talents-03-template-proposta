package com.zup.microservice.proposal.controllers;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.zup.microservice.card.entities.Card.CardStatus;
import com.zup.microservice.proposal.entities.Proposal;
import com.zup.microservice.proposal.entities.Proposal.ProposalStatus;

@JsonAutoDetect(fieldVisibility=Visibility.ANY)
public class ProposalResponse {

	final Long id;
	
	final String email;
	
	final ProposalStatus status;

	final CardStatus cardStatus;

	public ProposalResponse(Proposal proposal) {
		this.id = proposal.getId();
		this.email = proposal.getEmail();
		this.status = proposal.getStatus();
		this.cardStatus = proposal.hasCard() ? proposal.getCard().getStatus() :
							proposal.isEligible() ? CardStatus.EM_PROCESSAMENTO :
								CardStatus.NAO_APLICAVEL;
	}
	
}
