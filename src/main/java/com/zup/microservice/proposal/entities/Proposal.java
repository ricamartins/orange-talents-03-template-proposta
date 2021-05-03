package com.zup.microservice.proposal.entities;

import java.math.BigDecimal;
import java.util.function.Function;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.zup.microservice.card.entities.Card;

@Entity
@Table(name="tb_proposals")
public class Proposal {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank @Column(unique=true)
	@Convert(converter=EncryptionConverter.class)
	private String document;
	
	@NotBlank
	private String name;
	
	@NotBlank @Email
	private String email;
	
	@NotBlank
	private String address;
	
	@NotNull
	private BigDecimal salary;

	@Enumerated(EnumType.STRING)
	private ProposalStatus status;

	@OneToOne(mappedBy="proposal")
	private Card card;
	
	/*
	 * hibernate only
	 */
	@Deprecated
	private Proposal() {}

	public Proposal(@NotBlank String document, @NotBlank String name, @NotBlank @Email String email, @NotBlank String address,
			@NotNull BigDecimal salary) {
		this.document = document;
		this.name = name;
		this.email = email;
		this.address = address;
		this.salary = salary;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getDocument() {
		return document;
	}
	
	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
	
	public Card getCard() {
		return card;
	}
	
	public void setCard(Card card) {
		this.card = card;
	}
	
	public boolean hasCard() {
		return card != null;
	}
	
	public ProposalStatus getStatus() {
		return status;
	}
	
	public void setStatus(String solicitationStatus) {
		this.status = ProposalStatus.convert(solicitationStatus);
	}
	
	public boolean isEligible() {
		return status == ProposalStatus.ELEGIVEL;
	}
	
	public <R> R map(Function<Proposal, R> function) {
		return function.apply(this);
	}
	
	public enum ProposalStatus {
		NAO_ELEGIVEL, ELEGIVEL;
		
		public static ProposalStatus convert(String solicitationStatus) {
			switch (solicitationStatus) {
				case "COM_RESTRICAO":
					return NAO_ELEGIVEL;
				case "SEM_RESTRICAO":
					return ELEGIVEL;
				default:
					throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "erro:Indisponibilidade de serviço");
			}
		}
	}
	
}
