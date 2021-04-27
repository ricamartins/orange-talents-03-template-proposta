package com.zup.microservice.validations;

import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

	private Class<? extends Enum<?>> enumClass;
	private boolean ignoreCase;
	
	@Override
	public void initialize(ValidEnum annotation) {
		this.enumClass = annotation.value();
		ignoreCase = annotation.ignoreCase();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		String option = ignoreCase ? value.toUpperCase() : value;
		return Stream.of(enumClass.getEnumConstants())
				.anyMatch(e -> e.name().equals(option));
	}

	
}
