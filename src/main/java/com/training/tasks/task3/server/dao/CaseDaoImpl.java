package com.training.tasks.task3.server.dao;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.training.tasks.task3.server.domain.Case;

public class CaseDaoImpl implements CaseDAO {

	private static List<Case> caseList = new ArrayList<>();
	private static final String CASES_FILE_PATH = "src/main/resources/task3/cases.txt";
	private static int idCounter = 0;

	public CaseDaoImpl() {
		readFileToList();
		idCounter = caseList.size() + 1;
	}

	@Override
	public void save(Case entity) {
		entity.setId(idCounter++);
		caseList.add(entity);
		saveListToFile();

	}

	@Override
	public List<Case> getAll() {
		return caseList;
	}

	@Override
	public Optional<Case> findById(int id) {
		return caseList.stream()
				.filter(u -> u.getId() == id)
				.findFirst();
	}

	@Override
	public boolean removeById(int id) {
		boolean isSucces = caseList.removeIf(c -> c.getId() == id);
		saveListToFile();
		return isSucces;
	}

	private void saveListToFile() {
		try {
			FileWriter writer = new FileWriter(CASES_FILE_PATH);
			for (Case cs : caseList) {
				String str = "<case>"
						+ "<id>" + cs.getId() + "</id>"
						+ "<firstName>" + cs.getFirstName() + "</firstName>"
						+ "<lastName>" + cs.getLastName() + "</lastName>"
						+ "<group>" + cs.getGroup() + "</group>"
						+ "<course>" + cs.getCourse() + "</course>"
						+ "<faculty>" + cs.getFaculty().toString() + "</faculty>"
						+ "</case>";
				writer.write(str);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readFileToList() {
		String str = null;
		try {
			DataInputStream reader = new DataInputStream(new FileInputStream(CASES_FILE_PATH));
			int nBytesToRead = reader.available();
			if (nBytesToRead > 0) {
				byte[] bytes = new byte[nBytesToRead];
				reader.read(bytes);
				str = new String(bytes);

				Pattern caseRegex = Pattern.compile("<case>(.*?)</case>", Pattern.DOTALL);
				Matcher caseMatcher = caseRegex.matcher(str);
				Pattern caseFieldRegex = Pattern.compile("<([^<>]+)>([^<>]+)</\\1>");
				while (caseMatcher.find()) {
					String cs = caseMatcher.group(1);
					Matcher caseFieldMatcher = caseFieldRegex.matcher(cs);
					List<String> caseFields = new ArrayList<>();
					while (caseFieldMatcher.find()) {
						caseFields.add(caseFieldMatcher.group(2));
					}
					caseList.add(new Case(Integer.valueOf(caseFields.get(0)), caseFields.get(1), caseFields.get(2),
							caseFields.get(3), caseFields.get(4), caseFields.get(5)));
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean replaceCaseById(int id, Case cs) {
		System.out.println(id + " - " +  cs.toString());
		for (int i = 0; i < caseList.size(); i++) {
			if (caseList.get(i).getId() == id) {
				cs.setId(id);
				caseList.set(i, cs);
				saveListToFile();
				return true;
			}
		}
		return false;
	}

}
