package com.zup.microservice.card.entities;

import java.util.Optional;

import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

	@NewSpan
	Optional<Card> findByCardNumber(String id);

}
