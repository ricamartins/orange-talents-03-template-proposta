package com.zup.microservice.card.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.zup.microservice.proposal.entities.Proposal;
import com.zup.microservice.proposal.entities.Proposal.ProposalStatus;

@Entity
@Table(name="tb_cards")
public class Card {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String cardNumber;
	
	@Enumerated(EnumType.STRING)
	private CardStatus status;
	
	@OneToOne
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
	
	public Card(String cardNumber, CardStatus status, Proposal proposal) {
		this.cardNumber = cardNumber;
		this.status = status;
		this.proposal = proposal;
	}

	public Long getId() {
		return id;
	}
	
	public CardStatus getStatus() {
		return status;
	}
	
	public void addBiometry(Biometry biometry) {
		biometry.setCard(this);
		biometries.add(biometry);
	}

	public void addBlocking(Blocking blocking) {
		this.status = CardStatus.BLOQUEADO;
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
	
	public enum CardStatus {
		ATIVO, EM_PROCESSAMENTO, BLOQUEADO, NAO_APLICAVEL;
		
//		public static CardStatus convert(String solicitationStatus) {
//			switch (solicitationStatus) {
//				case "COM_RESTRICAO":
//					return NAO_ELEGIVEL;
//				case "SEM_RESTRICAO":
//					return ELEGIVEL;
//				default:
//					throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "erro:Indisponibilidade de serviço");
//			}
//		}
	}
}
