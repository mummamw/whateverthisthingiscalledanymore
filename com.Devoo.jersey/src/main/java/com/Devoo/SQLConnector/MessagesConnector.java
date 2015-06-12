package com.Devoo.SQLConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.Devoo.beans.*;

public class MessagesConnector extends AbstractConnector {
	
	private static MessagesConnector instance;

	private MessagesConnector() {
		this.table = "messages";
	}

	public static MessagesConnector getInstance() {
		if (instance == null) {
			instance = new MessagesConnector();
		}
		return instance;
	}

	public ArrayList<Messages> getMessages(Messages message) throws SQLException {
		ArrayList<Messages> messages = new ArrayList<Messages>();
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		String SQL = "SELECT * FROM " + table + " WHERE `id`="
				+ message.getId();
		ResultSet rs = stmt.executeQuery(SQL);
		while (rs.next()) {
			messages.add(createMessages(rs));
		}
		rs.close();
		stmt.close();
		con.close();
		return messages;
	}
	
	public ArrayList<Messages> getMessagesByConversation(Messages message) throws SQLException {
		ArrayList<Messages> messages = new ArrayList<Messages>();
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		String SQL = "SELECT * FROM " + table + " WHERE `conversation-id`="
				+ message.getConversation_id();
		ResultSet rs = stmt.executeQuery(SQL);
		while (rs.next()) {
			messages.add(createMessages(rs));
		}
		rs.close();
		stmt.close();
		con.close();
		return messages;
	}

	public void addMessages(Messages messages) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " LIMIT 1";
		ResultSet rs = stmt.executeQuery(SQL);
		rs.moveToInsertRow();
		rs.updateInt("id", messages.getId());
		rs.updateInt("conversation-id", messages.getConversation_id());
		rs.updateTime("time-sent", messages.getTime_sent());
		rs.updateString("from-username", messages.getFrom_username());
		rs.updateString("message", messages.getMessage());
		rs.insertRow();
		rs.close();
		stmt.close();
		con.close();
	}

	public void deleteMessages(Messages messages) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE id="
				+ messages.getId();
		ResultSet rs = stmt.executeQuery(SQL);
		if (rs.next()) {
			rs.deleteRow();
		} else {
			throw new SQLException("No messages with that id exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}

	public void updateMessages(Messages messages) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE id="
				+ messages.getId();
		ResultSet rs = stmt.executeQuery(SQL);
		if (rs.next()) {
			rs.updateInt("id", messages.getId());
			rs.updateInt("conversation-id", messages.getConversation_id());
			rs.updateTime("time-sent", messages.getTime_sent());
			rs.updateString("from-username", messages.getFrom_username());
			rs.updateString("message", messages.getMessage());
			rs.updateRow();
		} else {
			throw new SQLException("No messages with that id exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}

	private Messages createMessages(ResultSet rs) throws SQLException {
		return new Messages(rs.getInt("id"), rs.getInt("conversation-id"), 
				rs.getTime("time-sent"), rs.getString("from-username"), 
				rs.getString("message"));
	}

}
