package com.Devoo.SQLConnector;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class FriendsConnector extends AbstractConnector {
	
	private static FriendsConnector instance;
	
	private FriendsConnector() {
		this.table = "Friends";
	}
	
	public static FriendsConnector getInstance() {
		if(instance == null) {
			instance = new FriendsConnector();
		}
		return instance;
	}
	
	
}
