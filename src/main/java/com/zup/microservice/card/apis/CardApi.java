package com.zup.microservice.card.apis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zup.microservice.card.dto.BlockingRequest;
import com.zup.microservice.card.dto.CardApiResult;
import com.zup.microservice.card.dto.CardRequest;
import com.zup.microservice.card.dto.CardResponse;
import com.zup.microservice.card.dto.TravelNoticeRequest;

@FeignClient(value="accounts", url="${accounts.url}")
public interface CardApi {

	@PostMapping
	CardResponse create(CardRequest request);
	
	@GetMapping
	CardResponse getByProposalId(@RequestParam("idProposta") String proposalId);

	@GetMapping("/{id}")
	CardResponse getById(@PathVariable("id") String id);
	
	@PostMapping("/{id}/bloqueios")
	CardApiResult block(@PathVariable("id") String id, BlockingRequest request);

	@PostMapping("/{id}/avisos")
	CardApiResult travelNotice(@PathVariable("id") String id, TravelNoticeRequest request);

}
