package com.training.tasks.task3.server.service;

import java.util.Optional;

import com.training.tasks.task3.server.dao.UserDAO;
import com.training.tasks.task3.server.dao.UserDaoImpl;
import com.training.tasks.task3.server.domain.User;
import com.training.tasks.task3.server.security.PasswordEncrypter;

public class UserService {
	
	private final UserDAO userDao = new UserDaoImpl();
	private final static String USER_NOT_FOUND_MSG = "User with email %s not found.";
	private final EmailValidator emailValidator = new EmailValidator();
	
	public String signUpUser(User newUser) {
		boolean isValidEmail = emailValidator.test(newUser.getEmail());
		if (!isValidEmail) {
			return "ERROR: email not valid";
		}
		
		boolean userExist = userDao
				.findByEmail(newUser.getEmail())
				.isPresent();
		if (userExist) {
			return "ERROR: email already taken";
		}
		String encryptedPassword = PasswordEncrypter.encrypt(newUser.getPassword());
		newUser.setPassword(encryptedPassword);
		userDao.save(newUser);
		return "CREATED";
	}
	
	public Optional<User> logInUser(String login, String password) throws RuntimeException {
		boolean userExist = userDao
				.findByEmail(login)
				.isPresent();
		if (!userExist) {
			throw new RuntimeException("ERROR: user not found");
		}
		User user =  userDao
				.findByEmail(login)
				.get();
		if(!password.equals(PasswordEncrypter.decrypt(user.getPassword()))) {
			user = null;
			throw new RuntimeException("ERROR: invalid password");
		}
		return Optional.ofNullable(user);
	}
	
	

}
