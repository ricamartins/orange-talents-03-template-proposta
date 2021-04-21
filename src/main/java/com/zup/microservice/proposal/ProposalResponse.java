package com.zup.microservice.proposal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.zup.microservice.proposal.Proposal.ProposalStatus;

@JsonAutoDetect(fieldVisibility=Visibility.ANY)
public class ProposalResponse {

	final Long id;
	
	final String email;
	
	final ProposalStatus status;

	final String cardStatus;

	public ProposalResponse(Proposal proposal) {
		this.id = proposal.getId();
		this.email = proposal.getEmail();
		this.status = proposal.getStatus();
		this.cardStatus = proposal.isEligible() ?  proposal.hasCard() ?
				"PRONTO" : "EM_PROCESSAMENTO" : "NAO_APLICAVEL";
	}
	
}
