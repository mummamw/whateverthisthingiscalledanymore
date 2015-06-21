package com.Devoo.beans;

public class UserRoles {
	
	private String role_name, username;

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "UserRoles [role_name=" + role_name + ", username=" + username
				+ "]";
	}

	public UserRoles(String role_name, String username) {
		super();
		this.role_name = role_name;
		this.username = username;
	}
	
	public UserRoles() {}

}
