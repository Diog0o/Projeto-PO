package prr.client;

import prr.notifications.Notifications;

import java.util.ArrayList;

public interface DeliveryMethod {
	
	public String deliverNotifications(ArrayList<Notifications> notifications);

}
