package com.Devoo.beans;

public class UserActivities {
	private String username;
	private int activity_id;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(int activity_id) {
		this.activity_id = activity_id;
	}
	@Override
	public String toString() {
		return "UserActivities [username=" + username + ", activity_id="
				+ activity_id + "]";
	}
	public UserActivities(String username, int activity_id) {
		super();
		this.username = username;
		this.activity_id = activity_id;
	}
	
	public UserActivities(){}
}
