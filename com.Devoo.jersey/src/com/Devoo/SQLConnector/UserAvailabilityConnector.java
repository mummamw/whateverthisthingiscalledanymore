package com.Devoo.SQLConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.Devoo.beans.*;

public class UserAvailabilityConnector extends AbstractConnector {

	private static UserAvailabilityConnector instance;

	private UserAvailabilityConnector() {
		this.table = "`user-availability`";
	}

	public static UserAvailabilityConnector getInstance() {
		if (instance == null) {
			instance = new UserAvailabilityConnector();
		}
		return instance;
	}

	public ArrayList<UserAvailability> getUserAvailability(String username) throws SQLException {
		ArrayList<UserAvailability> userAvailability = new ArrayList<UserAvailability>();
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		String SQL = "SELECT * FROM " + table + " WHERE username='"
				+ username + "'";
		ResultSet rs = stmt.executeQuery(SQL);
		while (rs.next()) {
			userAvailability.add(createUserAvailability(rs));
		}
		rs.close();
		stmt.close();
		con.close();
		return userAvailability;
	}
	
	public ArrayList<UserAvailability> getAllUserAvailability() throws SQLException {
		ArrayList<UserAvailability> userAvailability = new ArrayList<UserAvailability>();
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		String SQL = "SELECT * FROM " + table;
		ResultSet rs = stmt.executeQuery(SQL);
		while (rs.next()) {
			userAvailability.add(createUserAvailability(rs));
		}
		rs.close();
		stmt.close();
		con.close();
		return userAvailability;
	}

	public void addUserAvailability(UserAvailability userAvailability) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " LIMIT 1";
		ResultSet rs = stmt.executeQuery(SQL);
		rs.moveToInsertRow();
		rs.updateString("username", userAvailability.getUsername());
		rs.updateTime("time-start", userAvailability.getTime_start());
		rs.updateTime("time-end", userAvailability.getTime_end());
		rs.insertRow();
		rs.close();
		stmt.close();
		con.close();
	}

	public void deleteUserAvailability(UserAvailability userAvailability) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE username='"
				+ userAvailability.getUsername() + "'";
		ResultSet rs = stmt.executeQuery(SQL);
		if (rs.next()) {
			rs.deleteRow();
		} else {
			throw new SQLException("No userAvailability with that username exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}

	public void updateUser(UserAvailability userAvailability) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE username='"
				+ userAvailability.getUsername() + "'";
		ResultSet rs = stmt.executeQuery(SQL);
		if (rs.next()) {
			rs.updateString("username", userAvailability.getUsername());
			rs.updateTime("time-start", userAvailability.getTime_start());
			rs.updateTime("time-end", userAvailability.getTime_end());
		} else {
			throw new SQLException("No userAvailability with that username exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}

	private UserAvailability createUserAvailability(ResultSet rs) throws SQLException {
		return new UserAvailability(rs.getString("username"), rs.getTime("time-start"), rs.getTime("time-end"));
	}
}
