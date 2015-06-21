package com.Devoo.rest;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Application;

import com.Devoo.beans.*;
import com.Devoo.SQLConnector.*;

@Path("/FriendsService")
public class FriendsService extends Application {
	
	private FriendsConnector friendsTable = FriendsConnector.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{username}")
	public Response getFriends(@PathParam("username") String username) {
		ArrayList<String> friendList = new ArrayList<String>();
		ArrayList<Friends> friends;
		try {
			friends = friendsTable.getFriends(username);
			for(Friends friend: friends) {
				friendList.add(friend.returnOtherFriend(username));
			}
			return Response.status(200).entity(friendList).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(e.getErrorCode()).entity(e.getMessage()).build();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addFriend")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addFriend(Friends friend) {	
		try {
			friendsTable.addFriend(friend);
			return Response.status(200).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(e.getErrorCode()).entity(e.getMessage()).build();
		}	
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{username1}&{username2}")
	public Response deleteFriend(@PathParam("username1") String username1, @PathParam("username2") String username2) {
		try {
			friendsTable.deleteFriend(new Friends(username1, username2));
			return Response.status(200).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(e.getErrorCode()).entity(e.getMessage()).build();
		}
	}
}
