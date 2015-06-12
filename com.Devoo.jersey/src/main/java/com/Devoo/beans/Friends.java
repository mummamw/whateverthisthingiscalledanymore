package com.Devoo.beans;

public class Friends {
	private String username_1, username_2;

	public String getUsername_1() {
		return username_1;
	}

	public void setUsername_1(String username_1) {
		this.username_1 = username_1;
	}

	public String getUsername_2() {
		return username_2;
	}

	public void setUsername_2(String username_2) {
		this.username_2 = username_2;
	}

	@Override
	public String toString() {
		return "Friends [username_1=" + username_1 + ", username_2="
				+ username_2 + "]";
	}

	public Friends(String username_1, String username_2) {
		super();
		this.username_1 = username_1;
		this.username_2 = username_2;
	}
	
	public String returnOtherFriend(String username) {
		if(username == this.username_1) {
			return this.username_2;
		} else {
			return this.username_1;
		}
	}
	
	public Friends() {}
}
