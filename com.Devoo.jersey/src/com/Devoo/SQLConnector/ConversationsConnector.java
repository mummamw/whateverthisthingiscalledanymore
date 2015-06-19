package com.Devoo.SQLConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.Devoo.beans.*;

public class ConversationsConnector extends AbstractConnector {

	private static ConversationsConnector instance;

	private ConversationsConnector() {
		this.table = "conversations";
	}

	public static ConversationsConnector getInstance() {
		if (instance == null) {
			instance = new ConversationsConnector();
		}
		return instance;
	}

	public ArrayList<Conversations> getConversations(Conversations conversation)
			throws SQLException {
		ArrayList<Conversations> conversations = new ArrayList<Conversations>();
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		String SQL = "SELECT * FROM " + table + " WHERE id="
				+ conversation.getId();
		ResultSet rs = stmt.executeQuery(SQL);
		while (rs.next()) {
			conversations.add(createConversations(rs));
		}
		rs.close();
		stmt.close();
		con.close();
		return conversations;
	}

	public void addConversations(Conversations conversations)
			throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " LIMIT 1";
		ResultSet rs = stmt.executeQuery(SQL);
		rs.moveToInsertRow();
		rs.updateInt("id", conversations.getId());
		rs.updateTime("last-modified-timestamp",
				conversations.getLast_modified_timestamp());
		rs.insertRow();
		rs.close();
		stmt.close();
		con.close();
	}

	public void deleteConversations(Conversations conversations)
			throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE id="
				+ conversations.getId();
		ResultSet rs = stmt.executeQuery(SQL);
		if (rs.next()) {
			rs.deleteRow();
		} else {
			throw new SQLException("No conversations with that id exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}

	public void updateConversations(Conversations conversations)
			throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE id="
				+ conversations.getId();
		ResultSet rs = stmt.executeQuery(SQL);
		if (rs.next()) {
			rs.updateInt("id", conversations.getId());
			rs.updateTime("last-modified-timestamp",
					conversations.getLast_modified_timestamp());
			rs.updateRow();
		} else {
			throw new SQLException("No conversations with that id exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}

	private Conversations createConversations(ResultSet rs) throws SQLException {
		return new Conversations(rs.getInt("id"),
				rs.getTime("last-modified-timestamp"));
	}

}
