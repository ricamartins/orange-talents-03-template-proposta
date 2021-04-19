package com.zup.microservice.proposal;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="tb_proposals")
public class Proposal {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String document;
	
	@NotBlank @Email
	private String email;
	
	@NotBlank
	private String address;
	
	@NotNull
	private BigDecimal salary;

	public Proposal() {}

	public Proposal(@NotBlank String document, @NotBlank @Email String email, @NotBlank String address,
			@NotNull BigDecimal salary) {
		this.document = document;
		this.email = email;
		this.address = address;
		this.salary = salary;
	}
	
	public Long getId() {
		return id;
	}
}
