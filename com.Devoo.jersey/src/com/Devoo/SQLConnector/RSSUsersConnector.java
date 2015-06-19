package com.Devoo.SQLConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.Devoo.beans.*;

public class RSSUsersConnector extends AbstractConnector {

	private static RSSUsersConnector instance;

	private RSSUsersConnector() {
		this.table = "`rss-users`";
	}

	public static RSSUsersConnector getInstance() {
		if (instance == null) {
			instance = new RSSUsersConnector();
		}
		return instance;
	}

	public ArrayList<RSSUsers> getRSSUsers(RSSUsers rssUsers)
			throws SQLException {
		ArrayList<RSSUsers> rSSUsers = new ArrayList<RSSUsers>();
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		String SQL = "SELECT * FROM " + table + " WHERE `username`='"
				+ rssUsers.getUsername() + "' AND `rss-name`='"
				+ rssUsers.getRss_name() + "'";
		ResultSet rs = stmt.executeQuery(SQL);
		while (rs.next()) {
			rSSUsers.add(createRSSUser(rs));
		}
		rs.close();
		stmt.close();
		con.close();
		return rSSUsers;
	}

	public void addRSSUsers(RSSUsers rSSUsers) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " LIMIT 1";
		ResultSet rs = stmt.executeQuery(SQL);
		rs.moveToInsertRow();
		rs.updateString("username", rSSUsers.getUsername());
		rs.updateString("rss-name", rSSUsers.getRss_name());
		rs.updateString("rss-username", rSSUsers.getRss_username());
		rs.updateString("rss-password", rSSUsers.getRss_password());
		rs.insertRow();
		rs.close();
		stmt.close();
		con.close();
	}

	public void deleteRSSUser(RSSUsers rSSUsers) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE `username`='"
				+ rSSUsers.getUsername() + "' AND `rss-name`='"
				+ rSSUsers.getRss_name() + "'";
		ResultSet rs = stmt.executeQuery(SQL);
		if (rs.next()) {
			rs.deleteRow();
		} else {
			throw new SQLException(
					"No rss-user with that username and rss-name exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}

	public void updateRSSUser(RSSUsers rSSUsers) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE `username`='"
				+ rSSUsers.getUsername() + "' AND `rss-name`='"
				+ rSSUsers.getRss_name() + "'";
		ResultSet rs = stmt.executeQuery(SQL);
		if (rs.next()) {
			rs.updateString("username", rSSUsers.getUsername());
			rs.updateString("rss-name", rSSUsers.getRss_name());
			rs.updateString("rss-username", rSSUsers.getRss_username());
			rs.updateString("rss-password", rSSUsers.getRss_password());
			rs.updateRow();
		} else {
			throw new SQLException(
					"No rss-user with that username and rss-name exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}

	private RSSUsers createRSSUser(ResultSet rs) throws SQLException {
		return new RSSUsers(rs.getString("username"), rs.getString("rss-name"),
				rs.getString("rss-username"), rs.getString("rss-password"));
	}

}
