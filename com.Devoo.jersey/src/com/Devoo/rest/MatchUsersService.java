package com.Devoo.rest;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Application;

import com.Devoo.SQLConnector.UsersConnector;
import com.Devoo.beans.*;
import com.Devoo.logic.*;

@Path("/MatchUsersService")
public class MatchUsersService extends Application {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{username}")
	public Response getMatches(@PathParam("username") String username) {
		ArrayList<Users> users = null;
		MatchUsers results = new MatchUsers();
		try {
			users = results.findMatchedUsers(UsersConnector.getInstance().getExactUser(username).get(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(users == null) {
			return Response.status(500).entity("Something went wrong!").build();
		}
		return Response.status(200).entity(users).build();
		
	}

}
