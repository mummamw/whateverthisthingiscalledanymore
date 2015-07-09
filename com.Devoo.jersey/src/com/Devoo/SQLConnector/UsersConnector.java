package com.Devoo.SQLConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.Devoo.beans.*;

public class UsersConnector extends AbstractConnector {

	private static UsersConnector instance;

	private UsersConnector() {
		this.table = "users";
	}

	public static UsersConnector getInstance() {
		if (instance == null) {
			instance = new UsersConnector();
		}
		return instance;
	}

	public ArrayList<Users> getUsers(String username) throws SQLException {
		ArrayList<Users> users = new ArrayList<Users>();
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		String SQL = "SELECT username, `first-name`, `last-name`, `picture-url` FROM " + table + " WHERE `username` LIKE '%"
				+ username + "%'";
		ResultSet rs = stmt.executeQuery(SQL);
		while (rs.next()) {
			users.add(createUser(rs));
		}
		rs.close();
		stmt.close();
		con.close();
		return users;
	}
	
	public ArrayList<Users> getExactUser(String username) throws SQLException {
		ArrayList<Users> users = new ArrayList<Users>();
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		String SQL = "SELECT * FROM " + table + " WHERE `username`='"
				+ username + "'";
		ResultSet rs = stmt.executeQuery(SQL);
		while (rs.next()) {
			users.add(createUser(rs));
		}
		rs.close();
		stmt.close();
		con.close();
		return users;
	}

	public void addUsers(Users user) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " LIMIT 1";
		ResultSet rs = stmt.executeQuery(SQL);
		rs.moveToInsertRow();
		rs.updateString("username", user.getUsername());
		rs.updateString("first-name", user.getFirst_name());
		rs.updateString("last-name", user.getLast_name());
		rs.updateString("password", user.getPassword());
		rs.updateString("email", user.getEmail());
		rs.updateString("phone-number", user.getPhone_number());
		rs.updateString("picture-url", user.getPicture_url());
		rs.updateString("side-setting-preference",
				user.getSide_setting_preference());
		rs.insertRow();
		rs.close();
		stmt.close();
		con.close();
	}

	public void deleteUser(Users user) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE username='"
				+ user.getUsername() + "'";
		ResultSet rs = stmt.executeQuery(SQL);
		if (rs.next()) {
			rs.deleteRow();
		} else {
			throw new SQLException("No user with that username exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}

	public void updateUser(Users user) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE username='"
				+ user.getUsername() + "'";
		ResultSet rs = stmt.executeQuery(SQL);
		if (rs.next()) {
			rs.updateString("username", user.getUsername());
			rs.updateString("first-name", user.getFirst_name());
			rs.updateString("last-name", user.getLast_name());
			rs.updateString("password", user.getPassword());
			rs.updateString("email", user.getEmail());
			rs.updateString("phone-number", user.getPhone_number());
			rs.updateString("picture-url", user.getPicture_url());
			rs.updateString("side-setting-preference",
					user.getSide_setting_preference());
			rs.updateRow();
		} else {
			throw new SQLException("No user with that username exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}

	private Users createUser(ResultSet rs) throws SQLException {
		return new Users(rs.getString("username"), rs.getString("first-name"),
				rs.getString("last-name"), rs.getString("password"),
				rs.getString("email"), rs.getString("phone-number"),
				rs.getString("picture-url"),
				rs.getString("side-setting-preference"));
	}
}
