package com.training.tasks.task1.domain;

import java.util.Objects;

public class PaperBook extends Book {
	private String callNumber;

	public PaperBook() {
		super();
	}

	public PaperBook(int id, String name, String author, String publisher, String yearOfPublication,
			String callNumber) {
		super(id, name, author, publisher, yearOfPublication);
		this.callNumber = callNumber;
	}

	public PaperBook(String name, String author, String publisher, String yearOfPublication, String callNumber) {
		super(name, author, publisher, yearOfPublication);
		this.callNumber = callNumber;
	}

	public String getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}

	@Override
	public String toString() {
		return "PaperBook [id=" + id + ", name=" + name + ", author=" + author + ", publisher="
				+ publisher + ", yearOfPublication=" + yearOfPublication
				+ ", callNumber=" + callNumber + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(callNumber);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		PaperBook other = (PaperBook) obj;
		return Objects.equals(callNumber, other.callNumber);
	}

}
