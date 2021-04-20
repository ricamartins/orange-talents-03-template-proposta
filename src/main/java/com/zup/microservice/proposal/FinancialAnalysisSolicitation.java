package com.zup.microservice.proposal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FinancialAnalysisSolicitation {

	@JsonProperty("documento")
	final String document;
	@JsonProperty("nome")
	final String name;
	@JsonProperty("idProposta")
	final String proposalId;
	@JsonProperty("resultadoSolicitacao")
	final String status;
	
	public FinancialAnalysisSolicitation(Proposal proposal) {
		this.document = proposal.getDocument();
		this.name = proposal.getName();
		this.proposalId = proposal.getId().toString();
		this.status = null;
	}

	@JsonCreator
	public FinancialAnalysisSolicitation(@JsonProperty("documento") String document, @JsonProperty("nome") String name,
			@JsonProperty("idProposta") String proposalId, @JsonProperty("resultadoSolicitacao") String status) {
		this.document = document;
		this.name = name;
		this.proposalId = proposalId;
		this.status = status;
	}
	
}
