package com.training.tasks.task3.server.dao;

import java.util.List;
import java.util.Optional;

import com.training.tasks.task3.server.domain.Case;


public interface CaseDAO {
	void save(Case entity);

	List<Case> getAll();
	
	Optional<Case> findById(int id);

	boolean removeById(int id);
	
	boolean replaceCaseById(int id, Case cs);
}
