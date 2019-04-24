package com.ideacrest.app.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class BeanValidatorUtility {

	private Validator validator;

	@Inject
	public BeanValidatorUtility(Validator validator) {
		this.validator = validator;
	}

	public <T> List<String> validateBean(T beans) {
		Set<ConstraintViolation<T>> violations = validator.validate(beans);
		List<String> validationMessages = new ArrayList<String>();
		if (violations.size() > 0) {
			for (ConstraintViolation<T> violation : violations) {
				validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
			}
		}
		return validationMessages;
	}

}
