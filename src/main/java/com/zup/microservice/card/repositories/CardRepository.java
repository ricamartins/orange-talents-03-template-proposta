package com.zup.microservice.card.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zup.microservice.card.entities.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

	Optional<Card> findByCardNumber(String id);

}
