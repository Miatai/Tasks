package com.training.tasks.task1.domain;

import java.util.Objects;

public class Book {
	protected String type = getClass().getName();
	protected int id;
	protected String name;
	protected String author;
	protected String publisher;
	protected String yearOfPublication;

	public Book() {
		super();
	}

	public Book(int id, String name, String author, String publisher, String yearOfPublication) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.yearOfPublication = yearOfPublication;
	}

	public Book(String name, String author, String publisher, String yearOfPublishing) {
		super();
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.yearOfPublication = yearOfPublishing;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getYearOfPublication() {
		return yearOfPublication;
	}

	public void setYearOfPublication(String yearOfPublishing) {
		this.yearOfPublication = yearOfPublishing;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", author=" + author + ", publisher=" + publisher
				+ ", yearOfPublication=" + yearOfPublication + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, id, name, publisher, yearOfPublication);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Book other = (Book) obj;
		return Objects.equals(author, other.author) && id == other.id && Objects.equals(name, other.name)
				&& Objects.equals(publisher, other.publisher)
				&& Objects.equals(yearOfPublication, other.yearOfPublication);
	}

}
