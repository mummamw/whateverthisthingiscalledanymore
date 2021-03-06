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

@Path("/UsersService")
public class UsersService extends Application {

	private UsersConnector usersTable = UsersConnector.getInstance();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{username}")
	public Response getUser(@PathParam("username") String username) {
		ArrayList<Users> users;
		try {
			users = usersTable.getUsers(username);
			return Response.status(200).entity(users).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(e.getErrorCode()).entity(e.getMessage())
					.build();
		}
	}

	@POST
	@Path("/addUser")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(Users user) {
		try {
			usersTable.addUsers(user);
			UserRolesConnector.getInstance().addUsersRole(new UserRoles("User", user.getUsername()));
			return Response.status(200).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(e.getErrorCode()).entity(e.getMessage())
					.build();
		}
	}

	@DELETE
	@Path("/deleteUser/{username}")
	public Response deleteUser(@PathParam("username") String username) {
		try {
			Users u = new Users();
			u.setUsername(username);
			UserRolesConnector.getInstance().deleteUsersRole(new UserRoles("User", username));
			usersTable.deleteUser(u);
			return Response.status(200).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(e.getErrorCode()).entity(e.getMessage())
					.build();
		}
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateUser")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(Users user) {
		try {
			usersTable.updateUser(user);
			return Response.status(200).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(e.getErrorCode()).entity(e.getMessage())
					.build();
		}
	}

}
