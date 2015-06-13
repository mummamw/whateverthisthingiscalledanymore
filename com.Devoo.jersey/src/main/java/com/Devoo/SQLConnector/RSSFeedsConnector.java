package com.Devoo.SQLConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.Devoo.beans.*;

public class RSSFeedsConnector extends AbstractConnector {

	private static RSSFeedsConnector instance;

	private RSSFeedsConnector() {
		this.table = "`rss-feeds`";
	}

	public static RSSFeedsConnector getInstance() {
		if (instance == null) {
			instance = new RSSFeedsConnector();
		}
		return instance;
	}

	public ArrayList<RSSFeeds> getRSSFeeds(RSSFeeds rSSFeeds) throws SQLException {
		ArrayList<RSSFeeds> rssFeeds = new ArrayList<RSSFeeds>();
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		String SQL = "SELECT * FROM " + table + " WHERE `rss-name`='"
				+ rSSFeeds.getRss_name() + "'";
		ResultSet rs = stmt.executeQuery(SQL);
		while (rs.next()) {
			rssFeeds.add(createRSSFeeds(rs));
		}
		rs.close();
		stmt.close();
		con.close();
		return rssFeeds;
	}

	public void addRSSFeeds(RSSFeeds rSSFeeds) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " LIMIT 1";
		ResultSet rs = stmt.executeQuery(SQL);
		rs.moveToInsertRow();
		rs.updateString("rss-name", rSSFeeds.getRss_name());
		rs.updateString("rss-url", rSSFeeds.getRss_url());
		rs.insertRow();
		rs.close();
		stmt.close();
		con.close();
	}

	public void deleteRSSFeeds(RSSFeeds rSSFeeds) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE `rss-name`='"
				+ rSSFeeds.getRss_name() + "'";
		ResultSet rs = stmt.executeQuery(SQL);
		if (rs.next()) {
			rs.deleteRow();
		} else {
			throw new SQLException("No RSSFeed with that name exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}

	public void updateRSSFeeds(RSSFeeds rSSFeeds) throws SQLException {
		Connection con = DriverManager.getConnection(this.host, this.username,
				this.password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		String SQL = "SELECT * FROM " + table + " WHERE `rss-name`='"
				+ rSSFeeds.getRss_name() + "'";
		ResultSet rs = stmt.executeQuery(SQL);
		if (rs.next()) {
			rs.updateString("rss-name", rSSFeeds.getRss_name());
			rs.updateString("rss-url", rSSFeeds.getRss_url());
			rs.updateRow();
		} else {
			throw new SQLException("No RSSFeed with that name exist!");
		}
		rs.close();
		stmt.close();
		con.close();
	}

	private RSSFeeds createRSSFeeds(ResultSet rs) throws SQLException {
		return new RSSFeeds(rs.getString("rss-name"), rs.getString("rss-url"));
	}
	
}
