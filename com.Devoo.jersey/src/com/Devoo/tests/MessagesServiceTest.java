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

public class MessagesServiceTest extends JerseyTest {
	
	String name = "testUser";
	String password = "test";
	String JerseyLocation = "http://localhost:8080/com.Devoo.jersey/rest/MessagesService";
	
	@Override
	protected AppDescriptor configure() {
		return new WebAppDescriptor.Builder().build();
	}
	
	@Before
	public void setup() throws SQLException {
		UsersConnector.getInstance().addUsers(createUsers());
		UserRolesConnector.getInstance().addUsersRole(new UserRoles("Admin", name));
		ConversationsConnector.getInstance().addConversations(createConversations());
	}
	
	@After
	public void teardown() throws SQLException {
		UsersConnector.getInstance().deleteUser(createUsers());
		ConversationsConnector.getInstance().deleteConversations(createConversations());
	}
 
	@Test
	public void getMessagesTest() throws JSONException,
			URISyntaxException, SQLException {

		MessagesConnector.getInstance().addMessages(createMessages());
		
		JSONArray json = makeWebResource(name, password, JerseyLocation, ""+1)
				.get(JSONArray.class);
		
		JSONObject message = json.getJSONObject(0);
		
		MessagesConnector.getInstance().deleteMessages(createMessages());

		assertEquals(1, message.getInt("id"));
		assertEquals(1, message.getInt("conversation_id"));
		assertEquals(name, message.getString("from_username"));
		assertEquals("Test", message.getString("message"));
	}
	
	@Test
	public void createMessagesTest() throws JSONException, URISyntaxException, JsonMappingException, JsonGenerationException, UniformInterfaceException, IOException, SQLException {
		makeWebResource(name, password, JerseyLocation+"/addMessages", null)
				.post(new JSONObject(convertToJson(createMessages())));
		Messages createdMessage = MessagesConnector.getInstance().getMessagesByConversation(1).get(0);
		MessagesConnector.getInstance().deleteMessages(createMessages());
		
		assertEquals(1, createdMessage.getId());
		assertEquals(1, createdMessage.getConversation_id());
		assertEquals(name, createdMessage.getFrom_username());
		assertEquals("Test", createdMessage.getMessage());
	}
	
	@Test
	public void updateMessagesTest() throws JSONException, URISyntaxException, JsonMappingException, JsonGenerationException, UniformInterfaceException, IOException, SQLException {
		Messages message = createMessages();
		MessagesConnector.getInstance().addMessages(message);
		message.setMessage("changed");
		makeWebResource(name, password, JerseyLocation+"/updateMessages", null)
				.put(new JSONObject(convertToJson(message)));
		Messages createdMessage = MessagesConnector.getInstance().getMessagesByConversation(1).get(0);
		MessagesConnector.getInstance().deleteMessages(message);
		
		assertEquals(1, createdMessage.getId());
		assertEquals(1, createdMessage.getConversation_id());
		assertEquals(name, createdMessage.getFrom_username());
		assertEquals("changed", createdMessage.getMessage());
	}
	
	@Test
	public void deleteMessagesTest() throws JSONException, URISyntaxException, JsonMappingException, JsonGenerationException, UniformInterfaceException, IOException, SQLException {
		MessagesConnector.getInstance().addMessages(createMessages());
		makeWebResource(name, password, JerseyLocation+"/deleteMessages",
				1+"").delete();
	}
	
	public static String convertToJson(Object object) throws JsonMappingException, JsonGenerationException, IOException {
		return (new ObjectMapper()).writeValueAsString(object);
	}
	
	private Users createUsers() {
		return new Users(name, "Test", "Test", password, "Test", "Test", "/", "LEFT");
	}
	
	private Messages createMessages() {
		return new Messages(1, 1, null, name, "Test");
	}
	
	private Conversations createConversations() {
		return new Conversations(1, null);
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
