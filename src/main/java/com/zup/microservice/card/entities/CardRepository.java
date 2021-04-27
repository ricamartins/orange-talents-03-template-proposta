package com.zup.microservice.card.entities;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

	Optional<Card> findByCardNumber(String id);

}
