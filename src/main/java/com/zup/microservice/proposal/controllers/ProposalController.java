package com.zup.microservice.proposal.controllers;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.zup.microservice.card.apis.CardApi;
import com.zup.microservice.card.apis.CardResponse;
import com.zup.microservice.card.entities.Card;
import com.zup.microservice.card.entities.Card.CardStatus;
import com.zup.microservice.card.entities.CardRepository;
import com.zup.microservice.proposal.apis.FinancialAnalysisApi;
import com.zup.microservice.proposal.apis.FinancialAnalysisSolicitation;
import com.zup.microservice.proposal.entities.Proposal;
import com.zup.microservice.proposal.entities.Proposal.ProposalStatus;
import com.zup.microservice.proposal.entities.ProposalRepository;

import feign.FeignException;

@RestController
@RequestMapping("/proposals")
public class ProposalController {

	private ProposalRepository repository;
	private CardRepository cardRepository;
	private FinancialAnalysisApi financialApi;
	private CardApi cardApi;
	
	public ProposalController(ProposalRepository repository, CardRepository cardRepository, FinancialAnalysisApi financialApi, CardApi cardApi) {
		this.repository = repository;
		this.cardRepository = cardRepository;
		this.financialApi = financialApi;
		this.cardApi = cardApi;
	}
	
	@GetMapping("/{id}") 
	public ResponseEntity<ProposalResponse> getById(@PathVariable Long id) {
		
		Optional<Proposal> proposal = repository.findById(id);
		if (proposal.isEmpty())
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(proposal.get().map(ProposalResponse::new));
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody @Valid ProposalRequest request, UriComponentsBuilder uriBuilder) {
		
		if (repository.findByDocument(request.document).isPresent())
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "document:Já há uma proposta atrelada a este documento");
		
		Proposal proposal = repository.save(request.toEntity());
		
		FinancialAnalysisSolicitation solicitation = financialApi.analyse(proposal.map(FinancialAnalysisSolicitation::new));

		proposal.setStatus(solicitation.status);
		
		repository.save(proposal);
		
		URI uri = uriBuilder.path("/proposals/{id}").build(proposal.getId());
		return ResponseEntity.created(uri).build();
	}
	
	@Scheduled(fixedDelay=5000)
	public void updateProposalsCard() {
		repository.findAllByStatusAndCardIsNull(ProposalStatus.ELEGIVEL).stream()
			.forEach(proposal -> {
				try {
					CardResponse response = cardApi.getByProposalId(proposal.getId().toString());
					Card card = response.toEntity(CardStatus.ATIVO, proposal);
					cardRepository.save(card);
				} catch (FeignException.NotFound e) {
					throw new ResponseStatusException(HttpStatus.valueOf(e.status()), "cartao:Não faço ideia");
				}
			});
	}
}
