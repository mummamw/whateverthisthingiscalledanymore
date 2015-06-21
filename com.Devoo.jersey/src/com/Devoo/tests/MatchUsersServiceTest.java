package com.Devoo.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Time;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.Devoo.beans.*;
import com.Devoo.SQLConnector.*;

public class MatchUsersServiceTest extends JerseyTest {
	
	String name = "testUser";
	String password = "test";
	String JerseyLocation = "http://localhost:8080/com.Devoo.jersey/rest/MatchUsersService";
	
	@Override
	protected AppDescriptor configure() {
		return new WebAppDescriptor.Builder().build();
	}
	
	@SuppressWarnings("deprecation")
	@Before
	public void setup() throws SQLException {
		UsersConnector.getInstance().addUsers(createUsers());
		UserRolesConnector.getInstance().addUsersRole(new UserRoles("Admin", name));
		Users user = createUsers();
		user.setUsername(name+2);
		UsersConnector.getInstance().addUsers(user);
		UserRolesConnector.getInstance().addUsersRole(new UserRoles("User", user.getUsername()));
		user.setUsername(name+3);
		UsersConnector.getInstance().addUsers(user);
		UserRolesConnector.getInstance().addUsersRole(new UserRoles("User", user.getUsername()));
		user.setUsername(name+4);
		UsersConnector.getInstance().addUsers(user);
		UserRolesConnector.getInstance().addUsersRole(new UserRoles("User", user.getUsername()));
		FriendsConnector.getInstance().addFriend(new Friends(name, name+2));
		FriendsConnector.getInstance().addFriend(new Friends(name, name+3));
		FriendsConnector.getInstance().addFriend(new Friends(name, name+4));
		UserAvailability userAvailability = createUserAvailability();
		UserAvailabilityConnector.getInstance().addUserAvailability(userAvailability);
		userAvailability.setUsername(name+2);
		UserAvailabilityConnector.getInstance().addUserAvailability(userAvailability);
		userAvailability.setUsername(name+3);
		UserAvailabilityConnector.getInstance().addUserAvailability(userAvailability);
		userAvailability.setTime_start(new Time(11,0,0));
		userAvailability.setTime_end(new Time(12,0,0));
		userAvailability.setUsername(name+4);
		UserAvailabilityConnector.getInstance().addUserAvailability(userAvailability);
		ActivitiesConnector.getInstance().addActivities(new Activities(1, "test"));
		ActivitiesConnector.getInstance().addActivities(new Activities(2, "test2"));
		UserActivitiesConnector.getInstance().addUserActivities(new UserActivities(name, 1));
		UserActivitiesConnector.getInstance().addUserActivities(new UserActivities(name+2, 2));
		UserActivitiesConnector.getInstance().addUserActivities(new UserActivities(name+3, 1));
		UserActivitiesConnector.getInstance().addUserActivities(new UserActivities(name+4, 2));
	}
	
	@After
	public void teardown() throws SQLException {
		ActivitiesConnector.getInstance().deleteActivities(new Activities(1, "test"));
		ActivitiesConnector.getInstance().deleteActivities(new Activities(2, "test2"));
		Users user = createUsers();
		UsersConnector.getInstance().deleteUser(user);
		user.setUsername(name+2);
		UsersConnector.getInstance().deleteUser(user);
		user.setUsername(name+3);
		UsersConnector.getInstance().deleteUser(user);
		user.setUsername(name+4);
		UsersConnector.getInstance().deleteUser(user);
	}
 
	@Test
	public void matchUsersTest() throws JSONException,
			URISyntaxException, SQLException {
		
		JSONArray json = makeWebResource(name, password, JerseyLocation, name)
				.get(JSONArray.class);
				
		JSONObject user = json.getJSONObject(0);
		
		assertEquals("testUser"+3, user.get("username"));
		assertEquals("test", user.get("password"));
		assertEquals("Test", user.get("first_name"));
		assertEquals("Test", user.get("last_name"));
		assertEquals("Test", user.get("email"));
		assertEquals("Test", user.get("phone_number"));
		assertEquals("/", user.get("picture_url"));
		assertEquals("LEFT", user.get("side_setting_preference"));
		
		user = json.getJSONObject(1);
		
		assertEquals("testUser"+2, user.get("username"));
		assertEquals("test", user.get("password"));
		assertEquals("Test", user.get("first_name"));
		assertEquals("Test", user.get("last_name"));
		assertEquals("Test", user.get("email"));
		assertEquals("Test", user.get("phone_number"));
		assertEquals("/", user.get("picture_url"));
		assertEquals("LEFT", user.get("side_setting_preference"));
	}
	
	public static String convertToJson(Object object) throws JsonMappingException, JsonGenerationException, IOException {
		return (new ObjectMapper()).writeValueAsString(object);
	}
	
	private Users createUsers() {
		return new Users(name, "Test", "Test", password, "Test", "Test", "/", "LEFT");
	}
	
	@SuppressWarnings("deprecation")
	private UserAvailability createUserAvailability() {
		return new UserAvailability(name, new Time(8,0,0), new Time(10,0,0));
	}
	
	public static WebResource.Builder makeWebResource(String username, String password, String baseUrl, String id) {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(username, password));
		String uri = UriBuilder.fromUri(baseUrl).build().toString();
		if (id != null) {
			uri += "/" + id;
		}
		return client.resource(uri).type(MediaType.APPLICATION_JSON);
	}

}
