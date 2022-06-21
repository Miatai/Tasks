package com.training.tasks.task3.server.dao;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.training.tasks.task3.server.domain.User;
import com.training.tasks.task3.server.domain.UserRole;

public class UserDaoImpl implements UserDAO {

	private static List<User> userList = new ArrayList<>();
	private static final String USERS_FILE_PATH = "src/main/resources/task3/users.txt";
	private static int idCounter = 0;

	public UserDaoImpl() {
		readToList();
		idCounter = userList.size() + 1;
	}

	@Override
	public void save(User entity) {
		entity.setId(idCounter++);
		userList.add(entity);
		saveToFile();

	}

	@Override
	public List<User> getAll() {
		return userList;
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userList.stream()
				.filter(u -> u.getEmail().equals(email))
				.findFirst();
	}

	private void saveToFile() {
		try {
			FileWriter writer = new FileWriter(USERS_FILE_PATH);
			for (User user : userList) {
				String str = "<user>"
						+ "<id>" + user.getId() + "</id>"
						+ "<firstName>" + user.getFirstName() + "</firstName>"
						+ "<lastName>" + user.getLastName() + "</lastName>"
						+ "<email>" + user.getEmail() + "</email>"
						+ "<password>" + user.getPassword() + "</password>"
						+ "<userRole>" + user.getUserRole().toString() + "</userRole>"
						+ "</user>";
				writer.write(str);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readToList() {
		String str = null;
		try {
			DataInputStream reader = new DataInputStream(new FileInputStream(USERS_FILE_PATH));
			int nBytesToRead = reader.available();
			if (nBytesToRead > 0) {
				byte[] bytes = new byte[nBytesToRead];
				reader.read(bytes);
				str = new String(bytes);

				Pattern userRegex = Pattern.compile("<user>(.*?)</user>", Pattern.DOTALL);
				Matcher userMatcher = userRegex.matcher(str);
				Pattern userFieldRegex = Pattern.compile("<([^<>]+)>([^<>]+)</\\1>");
				while (userMatcher.find()) {
					String user = userMatcher.group(1);
					Matcher userFieldMatcher = userFieldRegex.matcher(user);
					List<String> userFields = new ArrayList<>();
					while (userFieldMatcher.find()) {
						userFields.add(userFieldMatcher.group(2));
					}
					userList.add(new User(Integer.valueOf(userFields.get(0)), userFields.get(1), userFields.get(2),
							userFields.get(3), userFields.get(4), UserRole.valueOf(userFields.get(5))));
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
