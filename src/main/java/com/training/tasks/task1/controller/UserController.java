package com.training.tasks.task1.controller;

import java.util.Optional;

import com.training.tasks.task1.domain.User;
import com.training.tasks.task1.service.UserService;

public class UserController {

	private static final UserService userService = new UserService();

	public String register(User newUser) {
		return userService.signUpUser(newUser);
	}

	public Optional<User> login(String login, String password) {
		return userService.logInUser(login, password);
	}

}
