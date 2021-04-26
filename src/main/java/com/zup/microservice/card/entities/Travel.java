package com.zup.microservice.card.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="tb_travels")
public class Travel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String destination;
	
	private LocalDate endDate;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	private String ipAddress;
	
	private String userAgent;
	
	@ManyToOne
	private Card card;

	/*
	 * hibernate only
	 */
	@Deprecated
	private Travel() {}
	
	public Travel(String destination, LocalDate endDate, String ipAddress, String userAgent) {
		this.destination = destination;
		this.endDate = endDate;
		this.ipAddress = ipAddress;
		this.userAgent = userAgent;
	}
	
	void setCard(Card card) {
		this.card = card;
	}
	
}
