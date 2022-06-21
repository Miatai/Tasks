package com.training.tasks.task3.server.service;

import java.util.function.Predicate;

public class EmailValidator implements Predicate<String> {
	private final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

	@Override
	public boolean test(String t) {
		return t.matches(EMAIL_REGEX);
	}

}
