package com.training.tasks.task1.service;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import com.training.tasks.task1.dao.BookDAO;
import com.training.tasks.task1.dao.BookDaoImpl;
import com.training.tasks.task1.domain.Book;
import com.training.tasks.task1.domain.User;

public class BookService {

	private final ListPaginator bookPaginator = new ListPaginator();

	private final BookDAO bookDao = new BookDaoImpl();
	private final UserService userService = new UserService();
	private final EmailSender emailSender = new EmailService();

	public List<Book> getPageOfBooks(int page, int pageSize) {
		return bookPaginator.getPage(bookDao.getAll(), page, pageSize);
	}

	public int getMaxPageNumber(int pageSize) {
		return bookPaginator.getMaxPageNumber(bookDao.getAll(), pageSize);
	}

	public List<Book> searchBooksByName(String name) {
		return bookDao.getAll().stream()
				.filter(t -> t.getName().contains(name))
				.collect(Collectors.toList());
	}

	public List<Book> searchBooksByAuthor(String author) {
		return bookDao.getAll().stream()
				.filter(b -> b.getAuthor().contains(author))
				.collect(Collectors.toList());
	}

	public List<Book> searchBooksByPublisher(String publisher) {
		return bookDao.getAll().stream()
				.filter(b -> b.getPublisher().contains(publisher))
				.collect(Collectors.toList());
	}

	public List<Book> searchBooksByYearOfPublishing(String year) {
		return bookDao.getAll().stream()
				.filter(b -> b.getYearOfPublication().contains(year))
				.collect(Collectors.toList());
	}

	public List<Book> searchBookByBookType(Class<? extends Book> typeOfBook) {
		return bookDao.getAll().stream().filter(b -> b.getClass().equals(typeOfBook)).collect(Collectors.toList());
	}

	public void sendBookOfferEmail(Book offeredBook, User offeringUser) {
		String messageSubject = "Home library: offer to add a book.";
		String messageText = offeringUser.getFirstName() + " " + offeringUser.getLastName()
				+ " (" + offeringUser.getEmail() + ") offers to add a nw book in library. \n"
				+ offeredBook.toString();
		List<User> admins = userService.getAllAdmins();
		StringJoiner addresses = new StringJoiner(", ");
		admins.stream()
				.forEach(u -> addresses.add(u.getEmail()));
		emailSender.send(addresses.toString(), messageSubject, messageText);
	}

	public void addNewBook(Book book) {
		bookDao.save(book);
		String messageSubject = "Home library: new book";
		String messageText = "New book have been added in home library.\n"
				+ book.toString();
		List<User> users = userService.getAll();
		StringJoiner addresses = new StringJoiner(", ");
		users.stream()
				.forEach(u -> addresses.add(u.getEmail()));
		emailSender.send(addresses.toString(), messageSubject, messageText);
	}

	public boolean removeBook(int id) {
		return bookDao.removeBookById(id);
	}

	public int getCatalogSize() {
		return bookDao.getBookListSize();
	}

}
