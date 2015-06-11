package com.Devoo.SQLConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.Devoo.beans.*;

public class FriendsConnector extends AbstractConnector {
	
	private static FriendsConnector instance;
	
	private FriendsConnector() {
		this.table = "friends";
	}
	
	public static FriendsConnector getInstance() {
		if(instance == null) {
			instance = new FriendsConnector();
		}
		return instance;
	}
	
	public ArrayList<Friends> getFriends(String username) throws SQLException {
		ArrayList<Friends> friends = new ArrayList<Friends>();
		Connection con = DriverManager.getConnection(this.host, this.username, this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		String SQL = "SELECT * FROM " + table + " WHERE `username-1`='" + username+"' OR `username-2`='"+username+"'";
		ResultSet rs = stmt.executeQuery( SQL );
		while(rs.next()) {
			friends.add(createFriend(rs));
		}
		rs.close();
		stmt.close();
		con.close();
		return friends;
	}
	
	public void addFriend(Friends friend) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username, this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " LIMIT 1";
		ResultSet rs = stmt.executeQuery( SQL );
		rs.moveToInsertRow();
		rs.updateString("username-1", friend.getUsername_1());
		rs.updateString("username-2", friend.getUsername_2());
		rs.insertRow();
		rs.close();
		stmt.close();
		con.close();
	}
	
	public void deleteFriend(Friends friend) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username, this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE (`username-1`='" + friend.getUsername_1()+"' OR `username-1`='"+friend.getUsername_2()+"') AND (`username-2`='"+friend.getUsername_1()+"' OR `username-2`='"+friend.getUsername_2()+"')";
		ResultSet rs = stmt.executeQuery( SQL );
		if(rs.next()) {
			rs.deleteRow();
		} else {
			throw new SQLException("No friend pair with those usernames exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}
	
	private Friends createFriend(ResultSet rs) throws SQLException {
		return new Friends(rs.getString("username-1"), rs.getString("username-2"));
	}
	
}
