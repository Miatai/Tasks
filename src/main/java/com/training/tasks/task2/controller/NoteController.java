package com.training.tasks.task2.controller;

import java.time.LocalDate;
import java.util.List;

import com.training.tasks.task2.domain.Note;
import com.training.tasks.task2.service.NoteService;

public class NoteController {
	private static final NoteService noteService = new NoteService();

	public List<Note> getAllNotes() {
		return noteService.getNotes();
	}

	public boolean addNote(Note note) {
		return noteService.addNote(note);
	}

	public List<Note> findNoteByKeyWord(List<Note> notes, String keyword) {
		return noteService.findByKeyword(notes, keyword);
	}

	public List<Note> findByEmail(List<Note> notes, String email) {
		return noteService.findByEmail(notes, email);
	}

	public List<Note> findByDate(List<Note> notes, LocalDate date) {
		return noteService.findByDate(notes, date);
	}

	public List<Note> findBeforeDate(List<Note> notes, LocalDate date) {
		return noteService.findBeforeDate(notes, date);
	}

	public List<Note> findAfterDAte(List<Note> notes, LocalDate date) {
		return noteService.findAfterDate(notes, date);
	}

}
