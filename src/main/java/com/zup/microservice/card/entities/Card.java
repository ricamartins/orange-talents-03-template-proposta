package com.zup.microservice.card.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.zup.microservice.proposal.entities.Proposal;

@Entity
@Table(name="tb_cards")
public class Card {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String cardNumber;
	
	@OneToOne(mappedBy="card")
	private Proposal proposal;

	@OneToMany(mappedBy="card", cascade=CascadeType.ALL)
	private List<Biometry> biometries;
	
	@OneToMany(mappedBy="card", cascade=CascadeType.ALL)
	private List<Blocking> blockings;
	
	@OneToMany(mappedBy="card", cascade=CascadeType.ALL)
	private List<Travel> travels;
	
	@OneToMany(mappedBy="card", cascade=CascadeType.ALL)
	private List<DigitalWallet> digitalWallets;
	
	/*
	 * hibernate only
	 */
	@Deprecated
	private Card() {}
	
	public Card(String cardNumber, Proposal proposal) {
		this.cardNumber = cardNumber;
		this.proposal = proposal;
	}

	public Long getId() {
		return id;
	}
	
	public void addBiometry(Biometry biometry) {
		biometry.setCard(this);
		biometries.add(biometry);
	}

	public void addBlocking(Blocking blocking) {
		blocking.setCard(this);
		blockings.add(blocking);
	}

	public void addTravel(Travel travel) {
		travel.setCard(this);
		travels.add(travel);
	}

	public void addDigitalWallet(DigitalWallet digitalWallet) {
		digitalWallet.setCard(this);
		digitalWallets.add(digitalWallet);
	}

	public boolean hasDigitalWallet(@NotBlank String digitalWalletService) {
		return digitalWallets.stream()
				.anyMatch(dw -> dw.getDigitalWalletService().toString().equals(digitalWalletService.toUpperCase()));
	}
}
