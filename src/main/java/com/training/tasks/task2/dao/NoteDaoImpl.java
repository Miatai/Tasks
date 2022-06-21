package com.training.tasks.task2.dao;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.training.tasks.task2.domain.Note;

public class NoteDaoImpl implements NoteDAO {
	{
		readFromFile();
	}

	private static List<Note> noteList = new ArrayList<>();
	private static final String NOTES_FILE_PATH = "src/main/resources/task2/notes.txt";

	@Override
	public List<Note> getNotes() {
		return noteList;
	}

	@Override
	public boolean addNote(Note note) {
		noteList.add(note);
		return save();
	}

	@Override
	public boolean save() {
		saveToFile();
		return true;
	}

	private void readFromFile() {
		String str = null;
		try {
			DataInputStream reader = new DataInputStream(new FileInputStream(NOTES_FILE_PATH));
			int nBytesToRead = reader.available();
			if (nBytesToRead > 0) {
				byte[] bytes = new byte[nBytesToRead];
				reader.read(bytes);
				str = new String(bytes);

				Pattern noteRegex = Pattern.compile("<note>(.*?)</note>", Pattern.DOTALL);
				Matcher noteMatcher = noteRegex.matcher(str);
				Pattern noteElementRegex = Pattern.compile("<([^<>]+)>([^<>]+)</\\1>");
				while (noteMatcher.find()) {
					String note = noteMatcher.group(1);
					Matcher noteElementMatcher = noteElementRegex.matcher(note);
					List<String> noteElements = new ArrayList<>();
					while (noteElementMatcher.find()) {
						noteElements.add(noteElementMatcher.group(2));
					}
					noteList.add(new Note(noteElements.get(0), noteElements.get(1), noteElements.get(2),
							noteElements.get(3)));
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void saveToFile() {
		try {
			FileWriter writer = new FileWriter(NOTES_FILE_PATH);
			for (Note note : noteList) {
				String str = "<note>"
						+ "<subject>" + note.getSubject() + "</subject>"
						+ "<date>" + note.getDateOfCreation().format(Note.DATE_TIME_FORMATTER)
						+ "</date>"
						+ "<email>" + note.getEmail() + "</email>"
						+ "<message>" + note.getMessage() + "</message>"
						+ "</note>";
				writer.write(str);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
