package com.training.tasks.task2.dao;

import java.util.List;

import com.training.tasks.task2.domain.Note;

public interface NoteDAO {
	List<Note> getNotes();
	
	boolean addNote(Note note);
	
	boolean save();
}
