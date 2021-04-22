package com.zup.microservice.card;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.zup.microservice.proposal.Proposal;

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
	
	/*
	 * hibernate only
	 */
	@Deprecated
	private Card() {}
	
	public Card(String cardNumber, Proposal proposal) {
		this.cardNumber = cardNumber;
		this.proposal = proposal;
	}

	public void addBiometry(Biometry biometry) {
		biometry.setCard(this);
		biometries.add(biometry);
	}
	
}
