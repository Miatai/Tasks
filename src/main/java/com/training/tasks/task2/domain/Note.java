package com.training.tasks.task2.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

public class Note {
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd, MM, yyyy HH:mm:ss",
			Locale.ENGLISH);

	private String subject;
	private LocalDateTime dateOfCreation;
	private String email;
	private String message;

	public Note() {
		super();
	}

	public Note(String subject, String email, String message) {
		super();
		this.subject = subject;
		this.dateOfCreation = LocalDateTime.now();
		this.email = email;
		this.message = message;
	}

	public Note(String subject, String dateOfCreation, String email, String message) {
		super();
		this.subject = subject;
		this.dateOfCreation = LocalDateTime.parse(dateOfCreation, DATE_TIME_FORMATTER);
		this.email = email;
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public LocalDateTime getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation() {
		this.dateOfCreation = LocalDateTime.now();
	}

	public String getEmail() {
		return email;
	}

	public boolean setEmail(String email) {
		if (email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
			this.email = email;
			return true;
		}
		return false;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateOfCreation, email, message, subject);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Note other = (Note) obj;
		return Objects.equals(dateOfCreation, other.dateOfCreation) && Objects.equals(email, other.email)
				&& Objects.equals(message, other.message) && Objects.equals(subject, other.subject);
	}

	@Override
	public String toString() {
		return "Note: \nsubject: \"" + subject + "\"\ndateOfCreation: \""
				+ dateOfCreation.format(DATE_TIME_FORMATTER)
				+ "\"\nemail: \"" + email
				+ "\"\nmessage: \""
				+ message + "\"";
	}

	public static class Comparators {
		public static Comparator<Note> SUBJECT = new Comparator<Note>() {

			@Override
			public int compare(Note o1, Note o2) {
				return o1.subject.compareTo(o2.subject);
			}

		};
		public static Comparator<Note> EMAIL = new Comparator<Note>() {

			@Override
			public int compare(Note o1, Note o2) {
				return o1.email.compareTo(o2.email);
			}

		};
		public static Comparator<Note> DATE = new Comparator<Note>() {

			@Override
			public int compare(Note o1, Note o2) {
				return o1.dateOfCreation.compareTo(o2.dateOfCreation);
			}

		};

	}
}
