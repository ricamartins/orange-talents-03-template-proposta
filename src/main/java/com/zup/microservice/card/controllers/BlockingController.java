package com.zup.microservice.card.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zup.microservice.card.apis.CardApi;
import com.zup.microservice.card.dto.BlockingRequest;
import com.zup.microservice.card.dto.BlockingResult;
import com.zup.microservice.card.dto.CardResponse;
import com.zup.microservice.card.entities.Blocking;
import com.zup.microservice.card.entities.Card;
import com.zup.microservice.card.repositories.CardRepository;
import com.zup.microservice.validations.ResponseError;

@RestController
@RequestMapping("/cards/{id}")
public class BlockingController {

	private CardRepository repository;
	private CardApi cardApi;
	
	public BlockingController(CardRepository repository, CardApi cardApi) {
		this.repository = repository;
		this.cardApi = cardApi;
	}
	
	@PostMapping("/block")
	public ResponseEntity<?> blockCard(@PathVariable String id, HttpServletRequest request) {
		Optional<Card> optCard = repository.findByCardNumber(id);
		
		if (optCard.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseError("cardNumber:Número de cartão não cadastrado"));
		
		Card card = optCard.get();
		
		CardResponse cardResponse = cardApi.getById(id);
		System.out.println(cardResponse);
		if (cardResponse.isBlocked())
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(new ResponseError("card:Cartão já está bloqueado"));
		
		
		BlockingResult result = cardApi.block(id, new BlockingRequest("proposals"));
		
		if (!result.isBlocked())
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(new ResponseError("card:Não foi possível bloquear o cartão"));
		
		card.addBlocking(createBlockingEntity(request));
		
		repository.save(card);
		
		return ResponseEntity.ok().build();
	}

	private Blocking createBlockingEntity(HttpServletRequest request) {

		String ipAddress = request.getHeader("X-Forwarded-For");
		
		if (ipAddress == null)
			ipAddress = request.getRemoteAddr();
		
		String userAgent = request.getHeader("User-Agent");
		
		return new Blocking(ipAddress, userAgent);
	}
}
