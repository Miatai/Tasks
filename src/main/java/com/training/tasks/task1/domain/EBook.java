package com.training.tasks.task1.domain;

import java.util.Objects;

public class EBook extends Book {
	private String link;

	public EBook() {
		super();
	}

	public EBook(int id, String name, String author, String publisher, String description, String yearOfPublication,
			String link) {
		super(id, name, author, publisher, yearOfPublication);
		this.link = link;
	}

	public EBook(String name, String author, String publisher, String yearOfPublishing,
			String link) {
		super(name, author, publisher, yearOfPublishing);
		this.link = link;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "EBook [id=" + id + ", name=" + name + ", author=" + author + ", publisher="
				+ publisher + ", yearOfPublication=" + yearOfPublication
				+ ", link=" + link + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(link);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		EBook other = (EBook) obj;
		return Objects.equals(link, other.link);
	}

}
