package com.zup.microservice.card.controllers;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.zup.microservice.card.entities.Biometry;
import com.zup.microservice.card.entities.Card;
import com.zup.microservice.card.entities.CardRepository;

@RestController
@RequestMapping("/cards/{id}/biometries")
public class BiometryController {

	private CardRepository repository;

	public BiometryController(CardRepository repository) {
		this.repository = repository;
	}
	
	@PostMapping
	public ResponseEntity<Void> post(@PathVariable String id, @RequestBody @Valid BiometryRequest request, UriComponentsBuilder uriBuilder) {
		Optional<Card> optCard = repository.findByCardNumber(id);
		
		if (optCard.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "cardNumber:Número de cartão não cadastrado");
		
		Card card = optCard.get();
		Biometry biometry = request.convert();
		card.addBiometry(biometry);
		
		repository.save(card);
		
		URI uri = uriBuilder.path("/{id}").build(biometry.getId());
		return ResponseEntity.created(uri).build();
	}
}
