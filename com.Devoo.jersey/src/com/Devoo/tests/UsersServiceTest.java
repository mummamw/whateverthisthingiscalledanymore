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

public class UsersServiceTest extends JerseyTest {
	
	String name = "testUser";
	String password = "test";
	String JerseyLocation = "http://localhost:8080/com.Devoo.jersey/rest/UsersService";
	
	@Override
	protected AppDescriptor configure() {
		return new WebAppDescriptor.Builder().build();
	}
	
	@Before
	public void setup() throws SQLException {
		UsersConnector.getInstance().addUsers(createUsers());
		UserRolesConnector.getInstance().addUsersRole(new UserRoles("Admin", name));
	}
	
	@After
	public void teardown() throws SQLException {
		UsersConnector.getInstance().deleteUser(createUsers());
	}
 
	@Test
	public void getUsersTest() throws JSONException,
			URISyntaxException {
		
		JSONArray json = makeWebResource(name, password, JerseyLocation, name)
				.get(JSONArray.class);
		JSONObject j = json.getJSONObject(0);
		assertEquals("testUser", j.get("username"));
		assertEquals("test", j.get("password"));
		assertEquals("Test", j.get("first_name"));
		assertEquals("Test", j.get("last_name"));
		assertEquals("Test", j.get("email"));
		assertEquals("Test", j.get("phone_number"));
		assertEquals("/", j.get("picture_url"));
		assertEquals("LEFT", j.get("side_setting_preference"));
	}
	
	@Test
	public void createUsersTest() throws JSONException, URISyntaxException, JsonMappingException, JsonGenerationException, UniformInterfaceException, IOException, SQLException {
		Users user = createUsers();
		user.setUsername(name+2);
		makeWebResource(name, password, JerseyLocation+"/addUser", null)
				.post(new JSONObject(convertToJson(user)));
		Users createdUser = UsersConnector.getInstance().getExactUser(user.getUsername()).get(0);
		UserRolesConnector.getInstance().deleteUsersRole(new UserRoles("User", name+2));
		UsersConnector.getInstance().deleteUser(user);
		assertEquals("testUser2", createdUser.getUsername());
		assertEquals("test", createdUser.getPassword());
		assertEquals("Test", createdUser.getFirst_name());
		assertEquals("Test", createdUser.getLast_name());
		assertEquals("Test", createdUser.getEmail());
		assertEquals("Test", createdUser.getPhone_number());
		assertEquals("/", createdUser.getPicture_url());
		assertEquals("LEFT", createdUser.getSide_setting_preference());
	}
	
	@Test
	public void updateUsersTest() throws JSONException, URISyntaxException, JsonMappingException, JsonGenerationException, UniformInterfaceException, IOException, SQLException {
		Users user = createUsers();
		user.setUsername(name+2);
		UsersConnector.getInstance().addUsers(user);
		UserRolesConnector.getInstance().addUsersRole(new UserRoles("User", user.getUsername()));
		user.setFirst_name("changed");
		makeWebResource(name, password, JerseyLocation+"/updateUser", null)
				.put(new JSONObject(convertToJson(user)));
		Users createdUser = UsersConnector.getInstance().getExactUser(user.getUsername()).get(0);
		UserRolesConnector.getInstance().deleteUsersRole(new UserRoles("User", name+2));
		UsersConnector.getInstance().deleteUser(user);
		assertEquals("testUser2", createdUser.getUsername());
		assertEquals("test", createdUser.getPassword());
		assertEquals("changed", createdUser.getFirst_name());
		assertEquals("Test", createdUser.getLast_name());
		assertEquals("Test", createdUser.getEmail());
		assertEquals("Test", createdUser.getPhone_number());
		assertEquals("/", createdUser.getPicture_url());
		assertEquals("LEFT", createdUser.getSide_setting_preference());
	}
	
	@Test
	public void deleteUsersTest() throws JSONException, URISyntaxException, JsonMappingException, JsonGenerationException, UniformInterfaceException, IOException, SQLException {
		Users user = createUsers();
		user.setUsername(name+2);
		UsersConnector.getInstance().addUsers(user);
		UserRolesConnector.getInstance().addUsersRole(new UserRoles("User", user.getUsername()));
		makeWebResource(name, password, JerseyLocation+"/deleteUser",
				user.getUsername()).delete();
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
