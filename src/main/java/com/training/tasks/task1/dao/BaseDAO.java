package com.training.tasks.task1.dao;

import java.util.List;

public interface BaseDAO<T> {
	void save(T entity);

	List<T> getAll();

}
