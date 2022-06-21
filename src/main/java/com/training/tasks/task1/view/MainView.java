package com.training.tasks.task1.view;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import com.training.tasks.task1.controller.BookController;
import com.training.tasks.task1.controller.UserController;
import com.training.tasks.task1.domain.Book;
import com.training.tasks.task1.domain.BookBuilder;
import com.training.tasks.task1.domain.EBook;
import com.training.tasks.task1.domain.EBookBuilder;
import com.training.tasks.task1.domain.PaperBook;
import com.training.tasks.task1.domain.PaperBookBuilder;
import com.training.tasks.task1.domain.User;
import com.training.tasks.task1.domain.UserBuilder;

public class MainView {

	private final UserController userController = new UserController();
	private final BookController bookController = new BookController();
	private User currentUser;

	public void startingView() {
		Scanner sc = new Scanner(System.in);
		boolean logged = false;
		while (!logged) {
			System.out.println("Welcome to the library");
			System.out.println("1. Log In");
			System.out.println("2. Sign Up");
			System.out.println("0. Exit");
			System.out.println("Input your choice:");
			switch (getNumberFromConsole(sc, 0, 2)) {
			case 1:
				Optional<User> gotenUSer = logInView(sc);
				if (gotenUSer.isPresent()) {
					currentUser = gotenUSer.get();
					logged = true;
				} else {
					startingView();
				}
				break;
			case 2:
				signUpView(sc);
				startingView();
				break;
			case 0:
				System.out.println("BYE.");
				System.exit(0);
				break;
			default:
				System.err.println("invalid input");
				break;
			}
		}

		System.out.println("you're logged");
		mainMenuView(sc);
		sc.close();
	}

	private int getNumberFromConsole(Scanner sc, int min, int max) {
		int number = -1;
		boolean isCurrentNumber = false;
		while (!isCurrentNumber) {
			try {
				number = Integer.parseInt(sc.nextLine());
			} catch (InputMismatchException e) {
				System.err.println("invalid type of input");
				continue;
			}
			if (number < min || number > max) {
				System.err.println("invalid input: out of range");
				continue;
			}
			isCurrentNumber = true;
		}
		return number;
	}

	private Optional<User> logInView(Scanner sc) {
		System.out.println("Log In View");
		System.out.println("email:");
		String email = sc.nextLine();
		System.out.println("password:");
		String password = sc.nextLine();
		return userController.login(email, password);
	}

	private void signUpView(Scanner sc) {
		UserBuilder userBuilder = new UserBuilder();
		System.out.println("Sign Up");
		System.out.println("First Name:");
		userBuilder.setFirstName(sc.nextLine());
		System.out.println("Last Name:");
		userBuilder.setLastName(sc.nextLine());
		System.out.println("Email:");
		userBuilder.setEmail(sc.nextLine());
		System.out.println("Password:");
		userBuilder.setPassword(sc.nextLine());
		User newUser = userBuilder.build();
		System.out.println(userController.register(newUser));
	}

	private void mainMenuView(Scanner sc) {
		while (true) {
			System.out.println("Main menu");
			System.out.println("1. View book catalog");
			System.out.println("2. Search for a book in catalog");
			System.out.println("3. Offer to add a book to the catalog");
			if (currentUser.isAdmin()) {
				System.out.println("---ADMIN MENU---");
				System.out.println("4. Add book to the catalog");
				System.out.println("5. Remove book from catalog");
			}
			System.out.println("0. Exit to starting view");
			System.out.println("Input your choice:");

			int choice;
			if (currentUser.isAdmin()) {
				choice = getNumberFromConsole(sc, 0, 7);
			} else {
				choice = getNumberFromConsole(sc, 0, 4);
			}
			switch (choice) {
			case 1:
				BookCatalogView(sc);
				break;
			case 2:
				BookSearchView(sc);
				break;
			case 3:
				OfferToAddTheBook(sc);
				break;
			case 4:
				addBookToCatalogView(sc);
				break;
			case 5:
				removeBookFromCatalogView(sc);
				break;
			case 0:
				startingView();
				break;

			default:
				break;
			}
		}

	}

	private void BookCatalogView(Scanner sc) {
		System.out.println("Book catalog");
		System.out.println("--------------------------------------------------------------------------");
		int choice = 1;
		int booksOnPage = 5;
		int maxPage = bookController.getMaxPageNumber(booksOnPage);
		while (choice != 0) {
			bookController.viewCatalog(choice, booksOnPage).stream().forEach(System.out::println);
			System.out.println("--------------------------------------------------------------------------");
			System.out.println("Page: " + choice);
			System.out.println("--------------------------------------------------------------------------");
			System.out.println("input page number from 1 to " + maxPage + " or 0 end of viewing");
			System.out.println("Input your choice:");
			choice = getNumberFromConsole(sc, 0, maxPage);
		}
	}

	private void BookSearchView(Scanner sc) {
		System.out.println("Book Search Menu");
		System.out.println("1. by Name");
		System.out.println("2. by Author");
		System.out.println("3. by Publisher");
		System.out.println("4. by Year of publishing");
		System.out.println("5. by Book type");
		System.out.println("0. Exit");
		System.out.println("Input your choice:");
		switch (getNumberFromConsole(sc, 1, 5)) {
		case 1:
			System.out.println("Input name to search: ");
			bookController.searchBooksByName(sc.nextLine()).stream().forEach(System.out::println);
			break;
		case 2:
			System.out.println("Input author to search: ");
			bookController.searchBooksByAuthor(sc.nextLine()).stream().forEach(System.out::println);
			break;
		case 3:
			System.out.println("Input publisher to search: ");
			bookController.searchBooksByPublisher(sc.nextLine()).stream().forEach(System.out::println);
			break;
		case 4:
			System.out.println("Input year to search: ");
			bookController.searchBooksByYearOfPublishing(sc.nextLine()).stream().forEach(System.out::println);
			break;
		case 5:
			System.out.println("Choose type of book:");
			System.out.println("1. PaperBook");
			System.out.println("2.EBook");
			System.out.println("Input your choice:");
			switch (getNumberFromConsole(sc, 1, 2)) {
			case 1:
				bookController.searchBookByType(PaperBook.class).stream().forEach(System.out::println);
				break;
			case 2:
				bookController.searchBookByType(EBook.class).stream().forEach(System.out::println);
				break;

			default:
				System.err.println("invalid unput");
				break;
			}
			bookController.searchBookByType(EBook.class).stream().forEach(System.out::println);
			break;
		case 0:
			return;

		default:
			System.err.println("invalid input");
			return;
		}

	}

	private void OfferToAddTheBook(Scanner sc) {
		BookBuilder offeredBookBuilder = new BookBuilder();
		System.out.println("Input the details of the book.");
		System.out.println("book name: ");
		offeredBookBuilder.setName(sc.nextLine());
		System.out.println("author: ");
		offeredBookBuilder.setAuthor(sc.nextLine());
		System.out.println("publisher: ");
		offeredBookBuilder.setPublisher(sc.nextLine());
		System.out.println("year of publication: ");
		offeredBookBuilder.setYearOfPublication(sc.nextLine());
		bookController.offerToAddBook(offeredBookBuilder.build(), currentUser);
		// TODO: handle returned confirmation
	}

	private void addBookToCatalogView(Scanner sc) {
		System.out.println("Type of book");
		System.out.println("1. Paper Book");
		System.out.println("2. EBook");
		System.out.println("Input your choice:");
		switch (getNumberFromConsole(sc, 1, 2)) {
		case 1:
			PaperBookBuilder paperBookBuilder = new PaperBookBuilder();
			System.out.println("Input the details of the book.");
			System.out.println("book name: ");
			paperBookBuilder.setName(sc.nextLine());
			System.out.println("author: ");
			paperBookBuilder.setAuthor(sc.nextLine());
			System.out.println("publisher: ");
			paperBookBuilder.setPublisher(sc.nextLine());
			System.out.println("year of publication: ");
			paperBookBuilder.setYearOfPublication(sc.nextLine());
			System.out.println("call number: ");
			paperBookBuilder.setCallNumber(sc.nextLine());
			if (bookController.addNewBook(paperBookBuilder.build())) {
				System.out.println("Book successfully added");
			} else {
				System.out.println("Failed to add book to catalog");
			}
			;
			break;
		case 2:
			EBookBuilder eBookBuilder = new EBookBuilder();
			System.out.println("Input the details of the book.");
			System.out.println("book name: ");
			eBookBuilder.setName(sc.nextLine());
			System.out.println("author: ");
			eBookBuilder.setAuthor(sc.nextLine());
			System.out.println("publisher: ");
			eBookBuilder.setPublisher(sc.nextLine());
			System.out.println("year of publication: ");
			eBookBuilder.setYearOfPublication(sc.nextLine());
			System.out.println("link: ");
			eBookBuilder.setLink(sc.nextLine());
			if (bookController.addNewBook(eBookBuilder.build())) {
				System.out.println("Book successfully added");
			} else {
				System.out.println("Failed to add book to catalog");
			}
			;
			break;

		default:
			break;

		}
	}

	private void removeBookFromCatalogView(Scanner sc) {
		BookCatalogView(sc);
		System.out.println("input removed book id");
		bookController.removeBook(getNumberFromConsole(sc, 1, bookController.getCatalogSize()));
	}

}
