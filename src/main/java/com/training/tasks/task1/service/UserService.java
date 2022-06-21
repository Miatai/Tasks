package com.training.tasks.task1.service;

import java.util.List;
import java.util.Optional;
import com.training.tasks.task1.dao.UserDAO;
import com.training.tasks.task1.dao.UserDaoImpl;
import com.training.tasks.task1.domain.User;
import com.training.tasks.task1.exception.UserNotFoundException;
import com.training.tasks.task1.security.PasswordEncrypter;

public class UserService {

	private final UserDAO userDao = new UserDaoImpl();
	private final static String USER_NOT_FOUND_MSG = "User with email %s not found.";
	private final EmailValidator emailValidator = new EmailValidator();

	public User loadUserByUsername(String email) throws UserNotFoundException {
		return userDao.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
	}

	public String signUpUser(User newUser) {
		boolean isValidEmail = emailValidator.test(newUser.getEmail());
		if (!isValidEmail) {
//			throw new IllegalStateException("email not valid");
			return "email not valid";
		}
		
		boolean userExist = userDao
				.findByEmail(newUser.getEmail())
				.isPresent();
		if (userExist) {
//			throw new IllegalStateException("email already taken");
			return "email already taken";
		}
		String encryptedPassword = PasswordEncrypter.encrypt(newUser.getPassword());
		newUser.setPassword(encryptedPassword);
		userDao.save(newUser);
		return "User registered";
	}
	
	public Optional<User> logInUser(String login, String password) {
		boolean userExist = userDao
				.findByEmail(login)
				.isPresent();
		if (!userExist) {
//			throw new UserNotFoundException("user not found");
			System.err.println("invalid password");
			return Optional.ofNullable(null);
		}
		User user =  userDao
				.findByEmail(login)
				.get();
		if(!password.equals(PasswordEncrypter.decrypt(user.getPassword()))) {
			System.err.println("invalid password");
			user = null;
		}
		return Optional.ofNullable(user);
	}
	
	public List<User> getAllAdmins(){
		return userDao.getAllAdmin();
	}
	
	public List<User> getAll(){
		return userDao.getAll();
	}
	
}
