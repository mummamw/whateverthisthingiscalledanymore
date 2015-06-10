package com.Devoo.beans;

import java.sql.Time;

public class UserAvailability {
	private String username;
	private Time time_start, time_end;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Time getTime_start() {
		return time_start;
	}
	public void setTime_start(Time time_start) {
		this.time_start = time_start;
	}
	public Time getTime_end() {
		return time_end;
	}
	public void setTime_end(Time time_end) {
		this.time_end = time_end;
	}
	@Override
	public String toString() {
		return "UserAvailability [username=" + username + ", time_start="
				+ time_start + ", time_end=" + time_end + "]";
	}
	public UserAvailability(String username, Time time_start, Time time_end) {
		super();
		this.username = username;
		this.time_start = time_start;
		this.time_end = time_end;
	}
	
	public UserAvailability(){}
}
