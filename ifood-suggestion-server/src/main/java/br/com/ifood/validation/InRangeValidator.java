package br.com.ifood.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InRangeValidator implements ConstraintValidator<InRange, Double> {

	private Double min;
	private Double max;

	@Override
	public void initialize(InRange constraintAnnotation) {
		this.min = constraintAnnotation.min();
		this.max = constraintAnnotation.max();
	}

	@Override
	public boolean isValid(Double value, ConstraintValidatorContext context) {
		return value == null || (value >= min && value <= max);
	}
}
