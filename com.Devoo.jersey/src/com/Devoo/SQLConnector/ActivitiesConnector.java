package com.Devoo.SQLConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.Devoo.beans.*;

public class ActivitiesConnector extends AbstractConnector {
	
	private static ActivitiesConnector instance;

	private ActivitiesConnector() {
		this.table = "activities";
	}

	public static ActivitiesConnector getInstance() {
		if (instance == null) {
			instance = new ActivitiesConnector();
		}
		return instance;
	}

	public ArrayList<Activities> getActivities(Activities activity) throws SQLException {
		ArrayList<Activities> activities = new ArrayList<Activities>();
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		String SQL = "SELECT * FROM " + table + " WHERE id="
				+ activity.getId();
		ResultSet rs = stmt.executeQuery(SQL);
		while (rs.next()) {
			activities.add(createActivities(rs));
		}
		rs.close();
		stmt.close();
		con.close();
		return activities;
	}
	
	public ArrayList<Activities> getAllActivities() throws SQLException {
		ArrayList<Activities> activities = new ArrayList<Activities>();
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		String SQL = "SELECT * FROM " + table;
		ResultSet rs = stmt.executeQuery(SQL);
		while (rs.next()) {
			activities.add(createActivities(rs));
		}
		rs.close();
		stmt.close();
		con.close();
		return activities;
	}

	public void addActivities(Activities activities) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " LIMIT 1";
		ResultSet rs = stmt.executeQuery(SQL);
		rs.moveToInsertRow();
		rs.updateInt("id", activities.getId());
		rs.updateString("name", activities.getName());
		rs.insertRow();
		rs.close();
		stmt.close();
		con.close();
	}

	public void deleteActivities(Activities activities) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE id="
				+ activities.getId();
		ResultSet rs = stmt.executeQuery(SQL);
		if (rs.next()) {
			rs.deleteRow();
		} else {
			throw new SQLException("No Activities with that id exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}

	public void updateActivities(Activities activities) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE id="
				+ activities.getId();
		ResultSet rs = stmt.executeQuery(SQL);
		if (rs.next()) {
			rs.updateInt("id", activities.getId());
			rs.updateString("name", activities.getName());
			rs.updateRow();
		} else {
			throw new SQLException("No Activities with that id exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}

	private Activities createActivities(ResultSet rs) throws SQLException {
		return new Activities(rs.getInt("id"), rs.getString("name"));
	}

}
