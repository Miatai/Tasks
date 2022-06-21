package com.training.tasks.task3.server.service;

import java.util.Optional;

import com.training.tasks.task3.server.dao.CaseDAO;
import com.training.tasks.task3.server.dao.CaseDaoImpl;
import com.training.tasks.task3.server.domain.Case;

public class CaseService {
	
	private final CaseDAO caseDAO = new CaseDaoImpl();
	
	public String getAllCases() {
		StringBuilder sb = new StringBuilder();
		caseDAO.getAll().stream().forEach(c -> sb.append(c.toString()));
		return sb.toString();
	}
	
	public String getCaseById(String idStr) {
//		boolean userExist = userDao
//				.findByEmail(login)
//				.isPresent();
//		if (!userExist) {
//			throw new UserNotFoundException("user not found");
//		}
//		User user =  userDao
//				.findByEmail(login)
//				.get();
		
		String response = "";
		int id;
		try {
			id = Integer.parseInt(idStr);
			Optional<Case> cs = caseDAO.findById(id);
			if(!cs.isPresent()) {
				return String.format("ERROR: Case with id %d does not exist", id);
			}
			response = cs.get().toString();
		}catch (NumberFormatException e) {
			return "ERROR: Number Format Exception";
		}
		
		return response;
	}
	
	public String addNewCase(Case cs) {
		caseDAO.save(cs);
		return "SUCCESS";		
	}
	
	public String deleteCaseById(int id) {
		if(caseDAO.removeById(id)) {
			return "SUCCESS";
		}else {
			return "ERROR: failed to delete";
		}
	}
	
	public String modifyCaseById(int id, Case cs) {
		if(caseDAO.replaceCaseById(id, cs)) {
			return "SUCCESS";
		}else {
			return "ERROR: failed to modify";
		}
	}

}
