package com.training.tasks.task1.dao;

import java.util.Optional;

import com.training.tasks.task1.domain.Book;

public interface BookDAO extends BaseDAO<Book> {
	Optional<Book> findById(int id);

	boolean removeBookById(int id);

	public int getBookListSize();

}
