package com.training.tasks.task1.dao;

import java.util.List;
import java.util.Optional;

import com.training.tasks.task1.domain.User;

public interface UserDAO extends BaseDAO<User> {

	Optional<User> findByEmail(String email);

	List<User> getAllAdmin();

}
