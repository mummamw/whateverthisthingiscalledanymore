package com.Devoo.SQLConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.Devoo.beans.*;

public class MessagesParticipantConnector extends AbstractConnector {
	
	private static MessagesParticipantConnector instance;

	private MessagesParticipantConnector() {
		this.table = "messages-participant";
	}

	public static MessagesParticipantConnector getInstance() {
		if (instance == null) {
			instance = new MessagesParticipantConnector();
		}
		return instance;
	}

	public ArrayList<MessagesParticipant> getMessagesParticipant(String username) throws SQLException {
		ArrayList<MessagesParticipant> messagesParticipant = new ArrayList<MessagesParticipant>();
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		String SQL = "SELECT * FROM " + table + " WHERE `username`='"
				+ username + "'";
		ResultSet rs = stmt.executeQuery(SQL);
		while (rs.next()) {
			messagesParticipant.add(createMessagesParticipant(rs));
		}
		rs.close();
		stmt.close();
		con.close();
		return messagesParticipant;
	}

	public void addMessagesParticipant(MessagesParticipant messagesParticipant) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " LIMIT 1";
		ResultSet rs = stmt.executeQuery(SQL);
		rs.moveToInsertRow();
		rs.updateString("username", messagesParticipant.getUsername());
		rs.updateInt("conversation-id", messagesParticipant.getConversation_id());
		rs.updateTime("time-read", messagesParticipant.getTime_read());
		rs.insertRow();
		rs.close();
		stmt.close();
		con.close();
	}

	public void deleteMessagesParticipant(MessagesParticipant messagesParticipant) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE username='"
				+ messagesParticipant.getUsername() + "'";
		ResultSet rs = stmt.executeQuery(SQL);
		if (rs.next()) {
			rs.deleteRow();
		} else {
			throw new SQLException("No MessagesParticipant with that username exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}

	public void updateMessagesParticipant(MessagesParticipant messagesParticipant) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE username='"
				+ messagesParticipant.getUsername() + "'";
		ResultSet rs = stmt.executeQuery(SQL);
		if (rs.next()) {
			rs.updateString("username", messagesParticipant.getUsername());
			rs.updateInt("conversation-id", messagesParticipant.getConversation_id());
			rs.updateTime("time-read", messagesParticipant.getTime_read());
			rs.updateRow();
		} else {
			throw new SQLException("No MessagesParticipant with that username exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}

	private MessagesParticipant createMessagesParticipant(ResultSet rs) throws SQLException {
		return new MessagesParticipant(rs.getString("username"), rs.getInt("conversation-id"), rs.getTime("time-read"));
	}

}
