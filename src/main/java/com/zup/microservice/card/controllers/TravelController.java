package com.zup.microservice.card.controllers;

import java.net.URI;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
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
import com.zup.microservice.card.dto.CardApiResult;
import com.zup.microservice.card.dto.TravelRequest;
import com.zup.microservice.card.entities.Card;
import com.zup.microservice.card.entities.Travel;
import com.zup.microservice.card.repositories.CardRepository;
import com.zup.microservice.validations.ResponseError;

@RestController
@RequestMapping("/cards/{id}")
public class TravelController {

	private CardRepository repository;

	private CardApi cardApi;
	
	public TravelController(CardRepository repository, CardApi cardApi) {
		this.repository = repository;
		this.cardApi = cardApi;
	}
	
	@PostMapping("/travels")
	public ResponseEntity<?> post(@PathVariable String id, @RequestBody @Valid TravelRequest request,
			HttpServletRequest httpRequest, UriComponentsBuilder uriBuilder) {
		
		Optional<Card> optCard = repository.findByCardNumber(id);
		
		if (optCard.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseError("cardNumber:Número de cartão não cadastrado"));
		
		CardApiResult result = cardApi.travelNotice(id, request.toTravelNoticeRequest());
		
		if (!result.isCreated())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseError("card:Não foi possível gerar o aviso de viagem"));
		
		Card card = optCard.get();
		card.addTravel(createTravelEntity(request, httpRequest));
		repository.save(card);
		
		URI uri = uriBuilder.path("/{id}").build(card.getId());
		return ResponseEntity.created(uri).build();
	}
	
	private Travel createTravelEntity(TravelRequest travelRequest, HttpServletRequest request) {

		String ipAddress = request.getHeader("X-Forwarded-For");
		
		if (ipAddress == null)
			ipAddress = request.getRemoteAddr();
		
		String userAgent = request.getHeader("User-Agent");
		
		return travelRequest.toEntity(ipAddress, userAgent);
	}
}
