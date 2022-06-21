package com.training.tasks.task1.exception;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
