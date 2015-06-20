package com.Devoo.rest;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Application;

import com.Devoo.beans.*;
import com.Devoo.SQLConnector.*;

@Path("/MessagesService")
public class MessagesService extends Application {

	private MessagesConnector messagesConnector = MessagesConnector.getInstance();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{conversationID}")
	public Response getMessages(@PathParam("conversationID") int conversationID) {
		ArrayList<Messages> messages;
		try {
			messages = messagesConnector.getMessages(conversationID);
			return Response.status(200).entity(messages).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(e.getErrorCode()).entity(e.getMessage())
					.build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addMessages")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addMessages(Messages messages) {
		try {
			messagesConnector.addMessages(messages);
			ConversationsConnector.getInstance().updateConversations(new Conversations(messages.getConversation_id(), new Time(System.currentTimeMillis())));
			return Response.status(200).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(e.getErrorCode()).entity(e.getMessage())
					.build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deleteMessages")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteMessages(Messages messages) {
		try {
			messagesConnector.deleteMessages(messages);
			return Response.status(200).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(e.getErrorCode()).entity(e.getMessage())
					.build();
		}
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateMessages")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateMessages(Messages messages) {
		try {
			messagesConnector.updateMessages(messages);
			return Response.status(200).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(e.getErrorCode()).entity(e.getMessage())
					.build();
		}
	}

}
