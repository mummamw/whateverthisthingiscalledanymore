package com.Devoo.beans;

public class RSSFeeds {
	private String rss_name, rss_url;

	public String getRss_name() {
		return rss_name;
	}

	public void setRss_name(String rss_name) {
		this.rss_name = rss_name;
	}

	public String getRss_url() {
		return rss_url;
	}

	public void setRss_url(String rss_url) {
		this.rss_url = rss_url;
	}

	@Override
	public String toString() {
		return "RSSFeeds [rss_name=" + rss_name + ", rss_url=" + rss_url + "]";
	}

	public RSSFeeds(String rss_name, String rss_url) {
		super();
		this.rss_name = rss_name;
		this.rss_url = rss_url;
	}
	
	public RSSFeeds() {}
}
