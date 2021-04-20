package com.zup.microservice.proposal;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.zup.microservice.validations.CPForCNPJ;

public class ProposalRequest {

	@NotBlank @CPForCNPJ @Pattern(regexp="[0-9]+", message="Deve conter apenas dígitos")
	final String document;
	
	@NotBlank
	final String name;
	
	@NotBlank @Email
	final String email;
	
	@NotBlank
	final String address;
	
	@NotNull @Positive
	final BigDecimal salary;

	public ProposalRequest(@NotBlank @Pattern(regexp = "[0-9]+") String document, @NotBlank String name, 
			@NotBlank @Email String email, @NotBlank String address, @NotNull @Positive BigDecimal salary) {
		this.document = document;
		this.name = name;
		this.email = email;
		this.address = address;
		this.salary = salary;
	}

	public Proposal toEntity() {
		return new Proposal(document, name, email, address, salary);
	}
	
}
