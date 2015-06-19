package com.Devoo.beans;

import java.sql.Time;

public class Messages {
	private int id, conversation_id;
	private Time time_sent;
	private String from_username, message;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getConversation_id() {
		return conversation_id;
	}
	public void setConversation_id(int conversation_id) {
		this.conversation_id = conversation_id;
	}
	public Time getTime_sent() {
		return time_sent;
	}
	public void setTime_sent(Time time_sent) {
		this.time_sent = time_sent;
	}
	public String getFrom_username() {
		return from_username;
	}
	public void setFrom_username(String from_username) {
		this.from_username = from_username;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "Messages [id=" + id + ", conversation_id=" + conversation_id
				+ ", time_sent=" + time_sent + ", from_username="
				+ from_username + ", message=" + message + "]";
	}
	public Messages(int id, int conversation_id, Time time_sent,
			String from_username, String message) {
		super();
		this.id = id;
		this.conversation_id = conversation_id;
		this.time_sent = time_sent;
		this.from_username = from_username;
		this.message = message;
	}
	
	public Messages(){}
}
