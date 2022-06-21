package com.training.tasks.task1.domain;

public class PaperBookBuilder extends BookBuilder<PaperBookBuilder> {
	private String callNumber;

	public PaperBookBuilder setCallNumber(String callnumber) {
		this.callNumber = callnumber;
		return this;
	}

	@Override
	public Book build() {
		return new PaperBook(name, author, publisher, yearOfPublication, callNumber);
	}

}
