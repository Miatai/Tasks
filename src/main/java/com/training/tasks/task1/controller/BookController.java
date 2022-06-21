package com.training.tasks.task1.controller;

import java.util.List;

import com.training.tasks.task1.domain.Book;
import com.training.tasks.task1.domain.User;
import com.training.tasks.task1.service.BookService;

public class BookController {
	private static final BookService bookService = new BookService();

	public List<Book> viewCatalog(int page, int pageSize) {
		return bookService.getPageOfBooks(page, pageSize);
	}

	public int getMaxPageNumber(int pageSize) {
		return bookService.getMaxPageNumber(pageSize);
	}

	public List<Book> searchBooksByName(String name) {
		return bookService.searchBooksByName(name);
	}

	public List<Book> searchBooksByAuthor(String author) {
		return bookService.searchBooksByAuthor(author);
	}

	public List<Book> searchBooksByPublisher(String publisher) {
		return bookService.searchBooksByPublisher(publisher);
	}

	public List<Book> searchBooksByYearOfPublishing(String year) {
		return bookService.searchBooksByYearOfPublishing(year);
	}

	public List<Book> searchBookByType(Class<? extends Book> typeOfBook) {
		return bookService.searchBookByBookType(typeOfBook);
	}

	public boolean offerToAddBook(Book offeredBook, User offeringUser) {
		bookService.sendBookOfferEmail(offeredBook, offeringUser);
		return true;
	}

	public boolean addNewBook(Book book) {
		bookService.addNewBook(book);
		return true;
	}

	public boolean removeBook(int id) {
		bookService.removeBook(id);
		return bookService.removeBook(id);
	}

	public int getCatalogSize() {
		return bookService.getCatalogSize();
	}
}
