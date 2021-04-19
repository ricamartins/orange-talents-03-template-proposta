package com.zup.microservice.proposal;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/proposals")
public class ProposalController {

	private ProposalRepository repository;

	public ProposalController(ProposalRepository repository) {
		this.repository = repository;
	}
	
	@PostMapping
	public ResponseEntity<Void> createProposal(@RequestBody @Valid ProposalRequest request, UriComponentsBuilder uriBuilder) {
		Proposal proposal = repository.save(request.convert());
		URI uri = uriBuilder.path("/proposals/{id}").build(proposal.getId());
		return ResponseEntity.created(uri).build();
	}
}
