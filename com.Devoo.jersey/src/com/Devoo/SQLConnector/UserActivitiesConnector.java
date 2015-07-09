package com.Devoo.SQLConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.Devoo.beans.*;

public class UserActivitiesConnector extends AbstractConnector {
	
	private static UserActivitiesConnector instance;

	private UserActivitiesConnector() {
		this.table = "`user-activities`";
	}

	public static UserActivitiesConnector getInstance() {
		if (instance == null) {
			instance = new UserActivitiesConnector();
		}
		return instance;
	}

	public ArrayList<UserActivities> getUserActivities(String username) throws SQLException {
		ArrayList<UserActivities> userActivities = new ArrayList<UserActivities>();
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		String SQL = "SELECT * FROM " + table + " WHERE `username`='"
				+ username + "'";
		ResultSet rs = stmt.executeQuery(SQL);
		while (rs.next()) {
			userActivities.add(createUserActivities(rs));
		}
		rs.close();
		stmt.close();
		con.close();
		return userActivities;
	}

	public void addUserActivities(UserActivities userActivities) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " LIMIT 1";
		ResultSet rs = stmt.executeQuery(SQL);
		rs.moveToInsertRow();
		rs.updateString("username", userActivities.getUsername());
		rs.updateInt("activity-id", userActivities.getActivity_id());
		rs.insertRow();
		rs.close();
		stmt.close();
		con.close();
	}

	public void deleteUserActivities(UserActivities userActivities) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE username='"
				+ userActivities.getUsername() + "' AND `activity-id`="+userActivities.getActivity_id();
		ResultSet rs = stmt.executeQuery(SQL);
		if (rs.next()) {
			rs.deleteRow();
		} else {
			throw new SQLException("No user activities with that username and/or activity ID exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}
	
	public void deleteUserActivitiesByUser(String username) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE username='"
				+ username + "'";
		ResultSet rs = stmt.executeQuery(SQL);
		if (rs.next()) {
			rs.deleteRow();
		} else {
			throw new SQLException("No user activities with that username exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}

	public void updateUserActivities(UserActivities userActivities) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE username='"
				+ userActivities.getUsername() + "' AND `activity-id`="+userActivities.getActivity_id();
		ResultSet rs = stmt.executeQuery(SQL);
		if (rs.next()) {
			rs.updateString("username", userActivities.getUsername());
			rs.updateInt("activity-id", userActivities.getActivity_id());
			rs.updateRow();
		} else {
			throw new SQLException("No user activities with that username and/or activity ID exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}

	private UserActivities createUserActivities(ResultSet rs) throws SQLException {
		return new UserActivities(rs.getString("username"), rs.getInt("activity-id"));
	}
}
