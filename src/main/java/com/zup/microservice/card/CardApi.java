package com.zup.microservice.card;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="accounts", url="${accounts.url}")
public interface CardApi {

	@PostMapping
	CardResponse create(CardRequest request);
	
	@GetMapping
	CardResponse getByProposalId(@RequestParam("idProposta") String proposalId);
}
