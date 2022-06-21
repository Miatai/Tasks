package com.training.tasks.task1.domain;

public class EBookBuilder extends BookBuilder<EBookBuilder> {
	private String link;

	public EBookBuilder setLink(String link) {
		this.link = link;
		return this;
	}

	@Override
	public Book build() {
		return new EBook(name, author, publisher, yearOfPublication, link);
	}

}
