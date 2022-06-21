package com.training.tasks.task1.domain;

public class BookBuilder <S extends BookBuilder> {
	protected String name;
	protected String author;
	protected String publisher;
	protected String yearOfPublication;
	
	public S setName(String name) {
		this.name = name;
		return (S) this;
	}
	public S setAuthor(String author) {
		this.author = author;
		return (S) this;
	}
	public S setPublisher(String publisher) {
		this.publisher = publisher;
		return (S) this;
	}
	public S setYearOfPublication(String yearOfPublication) {
		this.yearOfPublication = yearOfPublication;
		return (S) this;
	}
	public Book build() {
		return new Book(name, author, publisher, yearOfPublication);
	}

}
