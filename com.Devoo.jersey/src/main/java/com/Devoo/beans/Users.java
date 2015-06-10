package com.Devoo.beans;

public class Users {
	private String username, first_name, last_name, password, email, phone_number, picture_url;
	private String side_setting_preference;
	public Users(String username, String first_name, String last_name,
			String password, String email, String phone_number,
			String picture_url, String side_setting_preference) {
		super();
		this.username = username;
		this.first_name = first_name;
		this.last_name = last_name;
		this.password = password;
		this.email = email;
		this.phone_number = phone_number;
		this.picture_url = picture_url;
		this.side_setting_preference = side_setting_preference;
	}
	public Users(){}
	@Override
	public String toString() {
		return "Users [username=" + username + ", first_name=" + first_name
				+ ", last_name=" + last_name + ", password=" + password
				+ ", email=" + email + ", phone_number=" + phone_number
				+ ", picture_url=" + picture_url + ", side_setting_preference="
				+ side_setting_preference + "]";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getPicture_url() {
		return picture_url;
	}
	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}
	public String getSide_setting_preference() {
		return side_setting_preference;
	}
	public void setSide_setting_preference(String side_setting_preference) {
		this.side_setting_preference = side_setting_preference;
	}
	
}
