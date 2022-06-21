package com.training.tasks.task2.domain;

import java.time.LocalDateTime;

public class NoteBuilder {
	private String subject;
	private LocalDateTime dateOfCreation;
	private String email;
	private String message;

	public NoteBuilder setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public NoteBuilder setEmail(String email) {
		this.email = email;
		return this;
	}

	public NoteBuilder setMessage(String message) {
		this.message = message;
		return this;
	}

	public Note build() {
		return new Note(subject, email, message);
	}
}
