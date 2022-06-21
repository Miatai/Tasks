package com.training.tasks.task2.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import com.training.tasks.task2.controller.NoteController;
import com.training.tasks.task2.domain.Note;
import com.training.tasks.task2.domain.NoteBuilder;

public class MainView {
	private static final NoteController noteController = new NoteController();
	private static final String DATE_FORMAT = "dd, MM, yyyy";

	public void mainViev() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Main menu");
			System.out.println("1. Print all notes");
			System.out.println("2. Add new note");
			System.out.println("3. find notes by key word");
			System.out.println("4. find by conditions");
			System.out.println("0. Exit");
			System.out.println("Input your choice:");
			switch (getNumberFromConsole(sc, 0, 4)) {
			case 1:
				noteController.getAllNotes().stream()
						.forEach(System.out::println);
				break;
			case 2:
				addNote(sc);
				break;
			case 3:
				System.out.println("Input key word:");
				noteController.findNoteByKeyWord(noteController.getAllNotes(), sc.nextLine()).stream()
						.forEach(System.out::println);
				break;
			case 4:
				List<Note> notes = findByCondition(sc);
				sortByUserChoice(notes, sc).stream().forEach(System.out::println);
				break;
			case 0:
				System.out.println("BYE.");
				sc.close();
				System.exit(0);
				break;
			default:
				sc.close();
				break;
			}
		}
	}

	private List<Note> sortByUserChoice(List<Note> notes, Scanner sc) {
		System.out.println("Select type of sorting:");
		System.out.println("1. by email");
		System.out.println("2. by subject");
		System.out.println("3. by date");
		System.out.println("0. do not sort");
		System.out.println("Input your choice:");
		switch (getNumberFromConsole(sc, 0, 3)) {
		case 1:
			Collections.sort(notes, Note.Comparators.EMAIL);
			break;
		case 2:
			Collections.sort(notes, Note.Comparators.SUBJECT);
			break;
		case 3:
			Collections.sort(notes, Note.Comparators.DATE);
			break;
		case 0:
			break;
		default:
			break;
		}
		return notes;
	}

	private List<Note> findByCondition(Scanner sc) {
		List<Note> notes = noteController.getAllNotes();
		System.out.println("Select conditions:");
		System.out.println("1. find by email");
		System.out.println("2. find by date");
		System.out.println("3. find before date");
		System.out.println("4. find after date");
		System.out.println("Input your choice (13):");
		String str = sc.nextLine();
		if (str.contains("1")) {
			System.out.println("Input email to find:");
			notes = noteController.findByEmail(notes, sc.nextLine());
		}
		if (str.contains("2")) {
			notes = noteController.findByDate(notes, dateInput(sc));
		} else {
			if (str.contains("3")) {
				notes = noteController.findBeforeDate(notes, dateInput(sc));
			} else {
				if (str.contains("4")) {
					notes = noteController.findAfterDAte(notes, dateInput(sc));
				}
			}
		}
		return notes;
	}

	public LocalDate dateInput(Scanner sc) {
		String str = "";
		LocalDate ld = null;
		while (ld == null) {
			System.out.println("Input date in format (dd, MM, yyyy)");
			str = sc.nextLine();
			try {
				ld = LocalDate.parse(str, DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.ENGLISH));
			} catch (DateTimeParseException e) {
				System.err.println("Invalid date format. Try again");
			}
		}
		return ld;
	}

	private List<Note> sorting(List<Note> notes, Scanner sc) {
		System.out.println("Sorting by:");
		System.out.println("1. Subject");
		System.out.println("2. Date");
		System.out.println("3. Email");
		System.out.println("0. Do not sort");
		System.out.println("Input your choice:");
		switch (getNumberFromConsole(sc, 0, 3)) {
		case 1:
			Collections.sort(notes, Note.Comparators.SUBJECT);
			break;
		case 2:
			Collections.sort(notes, Note.Comparators.DATE);
			break;
		case 3:
			Collections.sort(notes, Note.Comparators.EMAIL);
			break;
		case 0:
			System.out.println("okay");
			break;

		default:
			break;
		}
		return notes;
	}

	private void addNote(Scanner sc) {
		NoteBuilder noteBuilder = new NoteBuilder();
		System.out.println("Adding note");
		System.out.println("subject: ");
		noteBuilder.setSubject(sc.nextLine());
		System.out.println("email: ");
		noteBuilder.setEmail(sc.nextLine());
		System.out.println("message: ");
		noteBuilder.setMessage(sc.nextLine());
		if (noteController.addNote(noteBuilder.build())) {
			System.out.println("Note added");
		} else {
			System.out.println("Failed to add note");
		}
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

}
