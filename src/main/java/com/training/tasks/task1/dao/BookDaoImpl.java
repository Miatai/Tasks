package com.training.tasks.task1.dao;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.training.tasks.task1.dao.gson.RuntimeTypeAdapterFactory;
import com.training.tasks.task1.domain.Book;
import com.training.tasks.task1.domain.EBook;
import com.training.tasks.task1.domain.PaperBook;

public class BookDaoImpl implements BookDAO {
	private static final RuntimeTypeAdapterFactory<Book> bookAdapterFactory = RuntimeTypeAdapterFactory
			.of(Book.class, "type")
			.registerSubtype(EBook.class, EBook.class.getName())
			.registerSubtype(PaperBook.class, PaperBook.class.getName());
	private static final Gson gson = new GsonBuilder().registerTypeAdapterFactory(bookAdapterFactory).create();
	private static List<Book> bookList = new ArrayList<>();
	private static final String USER_FILE_PATH = "src/main/resources/task1/books.json";
	private static int idCounter = 0;

	public BookDaoImpl() {
		readFileToList();
		idCounter = bookList.size() + 1;
	}

	@Override
	public void save(Book entity) {
		entity.setId(idCounter++);
		bookList.add(entity);
		saveListToFile();
	}

	@Override
	public List<Book> getAll() {
		return bookList;
	}

	private void saveListToFile() {
		String str = gson.toJson(bookList);
		try {
			FileOutputStream outputStream = new FileOutputStream(USER_FILE_PATH);
			byte[] strToByte = str.getBytes();
			outputStream.write(strToByte);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readFileToList() {
		String str = null;
		try {
			DataInputStream reader = new DataInputStream(new FileInputStream(USER_FILE_PATH));
			int nBytesToRead = reader.available();
			if (nBytesToRead > 0) {
				byte[] bytes = new byte[nBytesToRead];
				reader.read(bytes);
				str = new String(bytes);
				bookList = gson.fromJson(str, new TypeToken<List<? extends Book>>() {
				}.getType());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Optional<Book> findById(int id) {
		return bookList.stream()
				.filter(u -> u.getId() == id)
				.findFirst();
	}

	@Override
	public boolean removeBookById(int id) {
		boolean isSucces = bookList.remove(findById(id).orElse(new Book()));
		saveListToFile();
		return isSucces;
	}

	@Override
	public int getBookListSize() {
		return bookList.size();
	}

}
