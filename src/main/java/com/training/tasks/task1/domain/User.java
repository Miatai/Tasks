package com.training.tasks.task1.domain;

import java.util.Objects;

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
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	
	public boolean isAdmin() {
		return userRole.equals(UserRole.ADMIN) ? true : false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, lastName, password, userRole);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) 
				&& Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) 
				&& Objects.equals(password, other.password)
				&& userRole == other.userRole;
	}

	@Override
	public String toString() {
		return "User [id="+ id
				+", firstName=" + firstName 
				+ ", lastName=" + lastName 
				+ ", email=" + email
				+ ", password=" + password 
				+ ", userRole=" + userRole + "]";
	}

}
