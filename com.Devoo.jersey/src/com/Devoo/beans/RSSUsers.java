package com.Devoo.beans;

public class RSSUsers {
	private String username, rss_name, rss_username, rss_password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRss_name() {
		return rss_name;
	}

	public void setRss_name(String rss_name) {
		this.rss_name = rss_name;
	}

	public String getRss_username() {
		return rss_username;
	}

	public void setRss_username(String rss_username) {
		this.rss_username = rss_username;
	}

	public String getRss_password() {
		return rss_password;
	}

	public void setRss_password(String rss_password) {
		this.rss_password = rss_password;
	}

	@Override
	public String toString() {
		return "RSSUsers [username=" + username + ", rss_name=" + rss_name
				+ ", rss_username=" + rss_username + ", rss_password="
				+ rss_password + "]";
	}

	public RSSUsers(String username, String rss_name, String rss_username,
			String rss_password) {
		super();
		this.username = username;
		this.rss_name = rss_name;
		this.rss_username = rss_username;
		this.rss_password = rss_password;
	}
	
	public RSSUsers(){}
}
