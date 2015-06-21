package com.Devoo.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.Devoo.beans.*;
import com.Devoo.SQLConnector.*;

public class MessagesParticipantServiceTest extends JerseyTest {
	
	String name = "testUser";
	String password = "test";
	String JerseyLocation = "http://localhost:8080/com.Devoo.jersey/rest/MessagesParticipantService";
	
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
	public void getMessagesParticipantTest() throws JSONException,
			URISyntaxException, SQLException {

		MessagesParticipantConnector.getInstance().addMessagesParticipant(createMessagesParticipant());
		
		JSONArray json = makeWebResource(name, password, JerseyLocation, name)
				.get(JSONArray.class);
		
		JSONObject message = json.getJSONObject(0);
		
		MessagesParticipantConnector.getInstance().deleteMessagesParticipant(createMessagesParticipant());

		assertEquals(name, message.getString("username"));
		assertEquals(1, message.getInt("conversation_id"));
	}
	
	@Test
	public void createMessagesParticipantTest() throws JSONException, URISyntaxException, JsonMappingException, JsonGenerationException, UniformInterfaceException, IOException, SQLException {
		makeWebResource(name, password, JerseyLocation+"/addMessagesParticipant", null)
				.post(new JSONObject(convertToJson(createMessagesParticipant())));
		MessagesParticipant createdMessageParticipant = MessagesParticipantConnector.getInstance().getMessagesParticipant(name).get(0);
		MessagesParticipantConnector.getInstance().deleteMessagesParticipant(createMessagesParticipant());
		assertEquals(name, createdMessageParticipant.getUsername());
		assertEquals(1, createdMessageParticipant.getConversation_id());
	}
	
	@Test
	public void updateMessagesParticipantTest() throws JSONException, URISyntaxException, JsonMappingException, JsonGenerationException, UniformInterfaceException, IOException, SQLException {
		MessagesParticipant messageParticipant = createMessagesParticipant();
		MessagesParticipantConnector.getInstance().addMessagesParticipant(messageParticipant);
		Long time = System.currentTimeMillis();
		messageParticipant.setTime_read(new Time(time));
		makeWebResource(name, password, JerseyLocation+"/updateMessagesParticipant", null)
				.put(new JSONObject(convertToJson(messageParticipant)));
		MessagesParticipant createdMessageParticipant = MessagesParticipantConnector.getInstance().getMessagesParticipant(name).get(0);
		MessagesParticipantConnector.getInstance().deleteMessagesParticipant(messageParticipant);
		assertEquals(name, createdMessageParticipant.getUsername());
		assertEquals(1, createdMessageParticipant.getConversation_id());
		assertNotNull(createdMessageParticipant.getTime_read());
	}
	
	@Test
	public void deleteMessagesParticipantTest() throws JSONException, URISyntaxException, JsonMappingException, JsonGenerationException, UniformInterfaceException, IOException, SQLException {
		MessagesParticipantConnector.getInstance().addMessagesParticipant(createMessagesParticipant());
		makeWebResource(name, password, JerseyLocation+"/"+name+"&"+1,
				null).delete();
	}
	
	public static String convertToJson(Object object) throws JsonMappingException, JsonGenerationException, IOException {
		return (new ObjectMapper()).writeValueAsString(object);
	}
	
	private Users createUsers() {
		return new Users(name, "Test", "Test", password, "Test", "Test", "/", "LEFT");
	}
	
	private MessagesParticipant createMessagesParticipant() {
		return new MessagesParticipant(name, 1, null);
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
