package com.training.tasks.task1.dao;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.training.tasks.task1.domain.User;
import com.training.tasks.task1.domain.UserRole;

public class UserDaoImpl implements UserDAO {
	private static final Gson gson = new Gson();
	private static List<User> userList = new ArrayList<>();
	private static final String USER_FILE_PATH = "src/main/resources/task1/users.json";
	private static int idCounter = 0;

	public UserDaoImpl() {
		readFileToList();
		idCounter = userList.size() + 1;
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userList.stream()
				.filter(u -> u.getEmail().equals(email))
				.findFirst();
	}

	@Override
	public List<User> getAll() {
		return userList;
	}

	@Override
	public void save(User entity) {
		entity.setId(idCounter++);
		userList.add(entity);
		saveListToFile();

	}

	private void saveListToFile() {
		String str = gson.toJson(userList);
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
				userList = gson.fromJson(str, new TypeToken<List<User>>() {
				}.getType());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<User> getAllAdmin() {
		return userList.stream()
				.filter(u -> u.getUserRole().equals(UserRole.ADMIN))
				.collect(Collectors.toList());
	}

}
