package com.Devoo.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

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
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.Devoo.beans.*;
import com.Devoo.SQLConnector.*;

public class FriendsServiceTest extends JerseyTest {
	
	String name = "testUser";
	String password = "test";
	String JerseyLocation = "http://localhost:8080/com.Devoo.jersey/rest/FriendsService";
	
	@Override
	protected AppDescriptor configure() {
		return new WebAppDescriptor.Builder().build();
	}
	
	@Before
	public void setup() throws SQLException {
		UsersConnector.getInstance().addUsers(createUsers());
		UserRolesConnector.getInstance().addUsersRole(new UserRoles("Admin", name));
		Users user = createUsers();
		user.setUsername(name+2);
		UsersConnector.getInstance().addUsers(user);
		UserRolesConnector.getInstance().addUsersRole(new UserRoles("User", user.getUsername()));
	}
	
	@After
	public void teardown() throws SQLException {
		UsersConnector.getInstance().deleteUser(createUsers());
		Users user = createUsers();
		user.setUsername(name+2);
		UsersConnector.getInstance().deleteUser(user);
	}
 
	@Test
	public void getFriendsTest() throws JSONException,
			URISyntaxException, SQLException {

		FriendsConnector.getInstance().addFriend(new Friends(name, name+2));
		
		JSONArray json = makeWebResource(name, password, JerseyLocation, name+2)
				.get(JSONArray.class);
		
		FriendsConnector.getInstance().deleteFriend(new Friends(name, name+2));
		
		assertEquals(name, json.getString(0));
	}
	
	@Test
	public void createFriendsTest() throws JSONException, URISyntaxException, JsonMappingException, JsonGenerationException, UniformInterfaceException, IOException, SQLException {
		Friends friend = new Friends(name, name+2);
		makeWebResource(name, password, JerseyLocation+"/addFriend", null)
				.post(new JSONObject(convertToJson(friend)));
		Friends createdFriend = FriendsConnector.getInstance().getFriends(name+2).get(0);
		FriendsConnector.getInstance().deleteFriend(friend);
		assertEquals(name, createdFriend.returnOtherFriend(name+2));
	}
	
	@Test
	public void deleteFriendsTest() throws JSONException, URISyntaxException, JsonMappingException, JsonGenerationException, UniformInterfaceException, IOException, SQLException {
		FriendsConnector.getInstance().addFriend(new Friends(name, name+2));
		makeWebResource(name, password, JerseyLocation+"/"+name+"&"+name+2,
				null).delete();
	}
	
	public static String convertToJson(Object object) throws JsonMappingException, JsonGenerationException, IOException {
		return (new ObjectMapper()).writeValueAsString(object);
	}
	
	private Users createUsers() {
		return new Users(name, "Test", "Test", password, "Test", "Test", "/", "LEFT");
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
