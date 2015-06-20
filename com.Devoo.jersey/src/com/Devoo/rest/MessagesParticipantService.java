package com.Devoo.rest;

import java.sql.SQLException;
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

@Path("/MessagesParticipantService")
public class MessagesParticipantService extends Application {

	private MessagesParticipantConnector messageParticipant = MessagesParticipantConnector.getInstance();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{username}")
	public Response getMessagesParticipant(@PathParam("username") String username) {
		ArrayList<MessagesParticipant> messagesParticipant;
		try {
			messagesParticipant = messageParticipant.getMessagesParticipant(username);
			return Response.status(200).entity(messagesParticipant).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(e.getErrorCode()).entity(e.getMessage())
					.build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addMessagesParticipant")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addMessagesParticipant(MessagesParticipant messagesParticipant) {
		try {
			messageParticipant.addMessagesParticipant(messagesParticipant);
			return Response.status(200).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(e.getErrorCode()).entity(e.getMessage())
					.build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deleteMessagesParticipant")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteMessagesParticipant(MessagesParticipant messagesParticipant) {
		try {
			messageParticipant.deleteMessagesParticipant(messagesParticipant);
			return Response.status(200).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(e.getErrorCode()).entity(e.getMessage())
					.build();
		}
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateMessagesParticipant")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateMessagesParticipant(MessagesParticipant messagesParticipant) {
		try {
			messageParticipant.updateMessagesParticipant(messagesParticipant);
			return Response.status(200).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(e.getErrorCode()).entity(e.getMessage())
					.build();
		}
	}

}
