package com.Devoo.beans;

import java.sql.Time;

public class MessagesParticipant {
	private String username;
	private int conversation_id;
	private Time time_read;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getConversation_id() {
		return conversation_id;
	}
	public void setConversation_id(int conversation_id) {
		this.conversation_id = conversation_id;
	}
	public Time getTime_read() {
		return time_read;
	}
	public void setTime_read(Time time_read) {
		this.time_read = time_read;
	}
	@Override
	public String toString() {
		return "MessagesParticipant [username=" + username
				+ ", conversation_id=" + conversation_id + ", time_read="
				+ time_read + "]";
	}
	public MessagesParticipant(String username, int conversation_id,
			Time time_read) {
		super();
		this.username = username;
		this.conversation_id = conversation_id;
		this.time_read = time_read;
	}
	
	public MessagesParticipant() {}
}
