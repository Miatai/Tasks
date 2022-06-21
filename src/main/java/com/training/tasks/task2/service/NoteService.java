package com.training.tasks.task2.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.training.tasks.task2.dao.NoteDAO;
import com.training.tasks.task2.dao.NoteDaoImpl;
import com.training.tasks.task2.domain.Note;

public class NoteService {

	private static final NoteDAO noteDao = new NoteDaoImpl();

	public boolean addNote(Note newNote) {
		noteDao.addNote(newNote);
		return true;
	}

	public List<Note> getNotes() {
		return noteDao.getNotes();
	}

	public List<Note> findByKeyword(List<Note> notes, String keyword) {
		return notes.stream()
				.filter((n) -> n.getMessage().contains(keyword))
				.collect(Collectors.toList());
	}

	public List<Note> findByEmail(List<Note> notes, String email) {
		return notes.stream()
				.filter((n) -> n.getEmail().matches(email))
				.collect(Collectors.toList());
	}

	public List<Note> findByDate(List<Note> notes, LocalDate date) {
		return notes.stream()
				.filter((n) -> n.getDateOfCreation().toLocalDate().isEqual(date))
				.collect(Collectors.toList());
	}

	public List<Note> findBeforeDate(List<Note> notes, LocalDate date) {
		return notes.stream()
				.filter((n) -> n.getDateOfCreation().toLocalDate().isBefore(date))
				.collect(Collectors.toList());
	}

	public List<Note> findAfterDate(List<Note> notes, LocalDate date) {
		return notes.stream()
				.filter((n) -> n.getDateOfCreation().toLocalDate().isAfter(date))
				.collect(Collectors.toList());
	}

}
