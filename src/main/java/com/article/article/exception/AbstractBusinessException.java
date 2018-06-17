package com.article.article.exception;

/**
 *
 * @author Janderson
 */
public abstract class AbstractBusinessException extends RuntimeException {

	AbstractBusinessException(String message) {
		super(message);
	}

}
