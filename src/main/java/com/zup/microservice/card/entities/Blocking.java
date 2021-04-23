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
@Table(name="tb_blockings")
public class Blocking {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	private LocalDateTime blockedAt;
	
	private String ipAddress;
	
	private String userAgent;

	@ManyToOne
	private Card card;
	//depre
	private Blocking() {}
	
	public Blocking(String ipAddress, String userAgent) {
		this.ipAddress = ipAddress;
		this.userAgent = userAgent;
	}
	
	void setCard(Card card) {
		this.card = card;
	}
	
}
