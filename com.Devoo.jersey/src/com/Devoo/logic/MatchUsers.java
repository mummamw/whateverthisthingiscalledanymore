package com.Devoo.logic;

import com.Devoo.beans.*;
import com.Devoo.SQLConnector.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class MatchUsers {
	
	public ArrayList<Users> findMatchedUsers(Users user) {
		ArrayList<Users> users = new ArrayList<Users>();
		ArrayList<Users> orderedUserMatches = new ArrayList<Users>();
		
		FriendsConnector friendsTable = FriendsConnector.getInstance();
		UsersConnector usersTable = UsersConnector.getInstance();
		UserAvailabilityConnector userAvailabilityTable = UserAvailabilityConnector.getInstance();
		UserActivitiesConnector userActivitiesTable = UserActivitiesConnector.getInstance();
		
		try {
			ArrayList<Friends> friends = friendsTable.getFriends(user.getUsername());
			UserAvailability userAvailability = userAvailabilityTable.getUserAvailability(user.getUsername()).get(0);
			
			//Create list of users based upon a users availability
			for(Friends friend: friends) {
				UserAvailability availability = userAvailabilityTable.getUserAvailability(friend.returnOtherFriend(user.getUsername())).get(0);
				if(userAvailability.getTime_start().after( availability.getTime_start())) {
					if(userAvailability.getTime_start().before(availability.getTime_end())) {
						users.add(usersTable.getUsers(availability.getUsername()).get(0));
					} else {
						continue;
					}
				} else {
					if(userAvailability.getTime_end().after(availability.getTime_start())) {
						users.add(usersTable.getUsers(availability.getUsername()).get(0));
					} else {
						continue;
					}
				}
			}
			
			ArrayList<UserActivities> userActivities = userActivitiesTable.getUserActivities(user.getUsername());
			int[][] activityCount = new int[users.size()][2];
			for(int i = 0; i < users.size(); i++) {
				activityCount[i][1] = i;
			}
			
			int iteratorCount = 0;
			for(Users userFriend: users) {
				ArrayList<UserActivities> activities = userActivitiesTable.getUserActivities(userFriend.getUsername());
				for(UserActivities uA: userActivities) {
					if(activities.contains(uA)) {
						activityCount[iteratorCount][0]++;
					}
				}
				iteratorCount++;
			}
			
			Arrays.sort(activityCount, new java.util.Comparator<int[]>() {
			    public int compare(int[] a, int[] b) {
			        return Integer.compare(b[0], a[0]);
			    }
			});
			
			for(int i = 0; i < users.size(); i++) {
				orderedUserMatches.add(users.get(activityCount[i][1]));
			}
			
			return orderedUserMatches;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch(IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
}
