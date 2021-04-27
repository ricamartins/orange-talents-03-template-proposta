package com.zup.microservice.proposal.apis;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zup.microservice.proposal.entities.Proposal;

public class FinancialAnalysisSolicitation {

	@JsonProperty("documento")
	public final String document;
	@JsonProperty("nome")
	public final String name;
	@JsonProperty("idProposta")
	public final String proposalId;
	@JsonProperty("resultadoSolicitacao")
	public final String status;
	
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
