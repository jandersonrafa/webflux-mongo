package com.article.article.service.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.article.article.exception.ValidateException;

/**
 *
 * @author Janderson
 */
@Service
public class ValidatorServiceImpl implements ValidatorService {

	@Autowired
	private Validator validator;

	@Override
	public <T> void validate(T object) {
		final Set<ConstraintViolation<T>> violations = validator.validate(object);
		if (!violations.isEmpty()) {
			throw new ValidateException(violations.stream().findFirst().get().getMessage());
		}
	}

}
