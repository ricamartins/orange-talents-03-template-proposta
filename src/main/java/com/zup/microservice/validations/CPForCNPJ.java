package com.zup.microservice.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy={})
@ConstraintComposition(CompositionType.OR)
@CPF
@CNPJ
public @interface CPForCNPJ {

	String message() default "Deve ser um documento válido";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
