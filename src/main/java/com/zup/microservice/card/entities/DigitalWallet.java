package com.zup.microservice.card.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tb_wallets")
public class DigitalWallet {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String externalId;
	
	private String email;
	
	@Enumerated(EnumType.STRING)
	private DigitalWalletService digitalWalletService;

	@ManyToOne
	private Card card;
	
	/*
	 * hibernate only
	 */
	@Deprecated
	private DigitalWallet() {}
	
	public DigitalWallet(String externalId, String email, DigitalWalletService digitaWalletService) {
		this.externalId = externalId;
		this.email = email;
		this.digitalWalletService = digitaWalletService;
	}

	public enum DigitalWalletService {
		PAYPAL
	}

	public DigitalWalletService getDigitalWalletService() {
		return digitalWalletService;
	}
	
	public Long getId() {
		return id;
	}
	
	void setCard(Card card) {
		this.card = card;
	}

}
