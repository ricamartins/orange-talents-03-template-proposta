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
import org.springframework.web.util.UriComponentsBuilder;

import com.zup.microservice.card.apis.CardApi;
import com.zup.microservice.card.apis.CardApiResult;
import com.zup.microservice.card.entities.Card;
import com.zup.microservice.card.entities.CardRepository;
import com.zup.microservice.card.entities.DigitalWallet;
import com.zup.microservice.validations.ResponseError;

import feign.FeignException;

@RestController
@RequestMapping("/cards/{id}")
public class DigitalWalletController {

	private CardRepository repository;
	private CardApi cardApi;

	public DigitalWalletController(CardRepository repository, CardApi cardApi) {
		this.repository = repository;
		this.cardApi = cardApi;
	}
	
	@PostMapping("/wallets")
	public ResponseEntity<?> associateDigitalWallet(@PathVariable String id, @RequestBody @Valid DigitalWalletRequest request,
			UriComponentsBuilder uriBuilder) {
		
		Optional<Card> optCard = repository.findByCardNumber(id);
		
		if (optCard.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseError("cardNumber:Número de cartão não cadastrado"));

		Card card = optCard.get();
		if (card.hasDigitalWallet(request.digitalWalletService))
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(new ResponseError("digitalWalletService:Carteira digital já cadastrada para este cartão"));
		
		CardApiResult result;
		try {
			result = cardApi.associateDigitalWallet(id, request.toFeignRequest());
		} catch (FeignException.UnprocessableEntity e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseError("card:Não foi possível associar o cartão com essa carteira digital"));
		}
		
		if (!result.isAssociated())
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseError("card:Não foi possível associar o cartão com essa carteira digital"));
		
		DigitalWallet wallet = request.toEntity(result.getId());
		card.addDigitalWallet(wallet);
		repository.save(card);
		
		URI uri = uriBuilder.path("/{id}").build(wallet.getId());
		return ResponseEntity.created(uri).build();
	}
}
