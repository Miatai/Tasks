package com.training.tasks.task3.server.dao;

import java.util.List;
import java.util.Optional;

import com.training.tasks.task3.server.domain.User;


public interface UserDAO {
	
	void save(User entity);

	List<User> getAll();
	
	Optional<User> findByEmail(String email);

}
