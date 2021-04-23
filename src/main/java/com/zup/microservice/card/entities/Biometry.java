package com.zup.microservice.card.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="tb_biometries")
public class Biometry {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String fingerprint;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@ManyToOne
	private Card card;

	@Deprecated
	private Biometry() {};
	
	public Biometry(String fingerprint) {
		this.fingerprint = fingerprint;
	}
	
	public Long getId() {
		return id;
	}
	
	void setCard(Card card) {
		this.card = card;
	}
}
