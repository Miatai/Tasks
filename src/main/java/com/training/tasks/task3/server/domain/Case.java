package com.training.tasks.task3.server.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Case {
	private int id;
	private String firstName;
	private String lastName;
	private String group;
	private String course;
	private String faculty;

	public Case() {
		super();
	}

	public Case(String firstName, String lastName, String group, String course, String faculty) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.group = group;
		this.course = course;
		this.faculty = faculty;
	}

	public Case(int id, String firstName, String lastName, String group, String course, String faculty) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.group = group;
		this.course = course;
		this.faculty = faculty;
	}
	
	public Case(String caseXML) {
			Pattern caseFieldRegex = Pattern.compile("<([^<>]+)>([^<>]+)</\\1>");
			Matcher caseFieldMatcher = caseFieldRegex.matcher(caseXML);
			List<String> caseFields = new ArrayList<>();
			while (caseFieldMatcher.find()) {
				caseFields.add(caseFieldMatcher.group(2));
			}
			this.firstName = caseFields.get(0);
			this.lastName = caseFields.get(1);
			this.group = caseFields.get(2);
			this.course = caseFields.get(3);
			this.faculty = caseFields.get(4);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	@Override
	public int hashCode() {
		return Objects.hash(course, faculty, firstName, group, id, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Case other = (Case) obj;
		return Objects.equals(course, other.course)
				&& Objects.equals(faculty, other.faculty)
				&& Objects.equals(firstName, other.firstName)
				&& Objects.equals(group, other.group)
				&& id == other.id
				&& Objects.equals(lastName, other.lastName);
	}

	@Override
	public String toString() {
		return "Case [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", group=" + group
				+ ", course=" + course + ", faculty=" + faculty + "]";
	}

}
