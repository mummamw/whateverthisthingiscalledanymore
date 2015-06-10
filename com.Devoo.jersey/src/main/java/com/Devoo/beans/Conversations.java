package com.Devoo.beans;

import java.sql.Time;

public class Conversations {
	
	private int id;
	private Time last_modified_timestamp;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Time getLast_modified_timestamp() {
		return last_modified_timestamp;
	}
	public void setLast_modified_timestamp(Time last_modified_timestamp) {
		this.last_modified_timestamp = last_modified_timestamp;
	}
	@Override
	public String toString() {
		return "Conversations [id=" + id + ", last_modified_timestamp="
				+ last_modified_timestamp + "]";
	}
	public Conversations(int id, Time last_modified_timestamp) {
		super();
		this.id = id;
		this.last_modified_timestamp = last_modified_timestamp;
	}
	
	public Conversations(){}
}
