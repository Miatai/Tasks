package com.training.tasks.task3.server.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private UserRole userRole;

	public User() {
		super();
	}

	public User(String firstName, String lastName, String email, String password, UserRole userRole) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.userRole = userRole;
	}

	public User(int id, String firstName, String lastName, String email, String password, UserRole userRole) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.userRole = userRole;
	}

	public User(String userXML, UserRole userRole) {
		Pattern userFieldRegex = Pattern.compile("<([^<>]+)>([^<>]+)</\\1>");
		Matcher userFieldMatcher = userFieldRegex.matcher(userXML);
		List<String> userFields = new ArrayList<>();
		while (userFieldMatcher.find()) {
			userFields.add(userFieldMatcher.group(2));
		}

		this.firstName = userFields.get(0);
		this.lastName = userFields.get(1);
		this.email = userFields.get(2);
		this.password = userFields.get(3);
		this.userRole = userRole;
	}

	public boolean isAdmin() {
		return userRole.equals(UserRole.ADMIN) ? true : false;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, id, lastName, password, userRole);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		User other = (User) obj;
		return Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName)
				&& id == other.id
				&& Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password)
				&& userRole == other.userRole;
	}

	@Override
	public String toString() {
		return "User [id=" + id
				+ ", firstName=" + firstName
				+ ", lastName=" + lastName
				+ ", email=" + email
				+ ", password=" + password
				+ ", userRole=" + userRole + "]";
	}
}
