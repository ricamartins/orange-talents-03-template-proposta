package com.zup.microservice.proposal;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/proposals")
public class ProposalController {

	private ProposalRepository repository;
	private FinancialAnalysisApi financialAPI;
	
	public ProposalController(ProposalRepository repository, FinancialAnalysisApi financialAPI) {
		this.repository = repository;
		this.financialAPI = financialAPI;
	}
	
	@PostMapping
	public ResponseEntity<Void> createProposal(@RequestBody @Valid ProposalRequest request, UriComponentsBuilder uriBuilder) {
		
		if (repository.findByDocument(request.document).isPresent())
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "document:Já há uma proposta atrelada a este documento");
		
		Proposal proposal = repository.save(request.toEntity());

		FinancialAnalysisSolicitation solicitation = financialAPI.analyse(proposal.map(FinancialAnalysisSolicitation::new));

		proposal.setStatus(solicitation.status);
		
		repository.save(proposal);
		
		URI uri = uriBuilder.path("/proposals/{id}").build(proposal.getId());
		return ResponseEntity.created(uri).build();
	}

}
