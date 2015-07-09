package com.Devoo.logic;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import com.Devoo.SQLConnector.*;
import com.Devoo.beans.*;

public class DeleteOldActivities {

	private UserAvailabilityConnector userAvailabilityTable = UserAvailabilityConnector
			.getInstance();
	private UserActivitiesConnector userActivitiesTable = UserActivitiesConnector
			.getInstance();
	private ConversationsConnector conversationsTable = ConversationsConnector.getInstance();
	private MessagesParticipantConnector messagesParticipantTable = MessagesParticipantConnector.getInstance();

	public static void main(String[] args) {
		DeleteOldActivities activities = new DeleteOldActivities();
		while (true) {
			activities.deleteOldEntries();
			try {
				Thread.sleep(900000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void deleteOldEntries() {
		try {
			ArrayList<UserAvailability> availability = userAvailabilityTable
					.getAllUserAvailability();
			for (UserAvailability ava : availability) {
				if (ava.getTime_end().before(
						new Date(System.currentTimeMillis()))) {
					userAvailabilityTable.deleteUserAvailability(ava);
					userActivitiesTable.deleteUserActivitiesByUser(ava.getUsername());
					ArrayList<MessagesParticipant> messagesParticipant = messagesParticipantTable.getMessagesParticipant(ava.getUsername());
					for(MessagesParticipant mp: messagesParticipant) {
						Conversations conversation = new Conversations(mp.getConversation_id(), null);
						conversationsTable.deleteConversations(conversation);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
