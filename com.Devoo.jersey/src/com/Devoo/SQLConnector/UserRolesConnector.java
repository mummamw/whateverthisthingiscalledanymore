package com.Devoo.SQLConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.Devoo.beans.*;

public class UserRolesConnector extends AbstractConnector {

	private static UserRolesConnector instance;

	private UserRolesConnector() {
		this.table = "`user-roles`";
	}

	public static UserRolesConnector getInstance() {
		if (instance == null) {
			instance = new UserRolesConnector();
		}
		return instance;
	}

	public void addUsersRole(UserRoles userRole) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " LIMIT 1";
		ResultSet rs = stmt.executeQuery(SQL);
		rs.moveToInsertRow();
		rs.updateString("username", userRole.getUsername());
		rs.updateString("role-name", userRole.getRole_name());
		rs.insertRow();
		rs.close();
		stmt.close();
		con.close();
	}

	public void deleteUsersRole(UserRoles userRole) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE username='"
				+ userRole.getUsername() + "'";
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
	
}
