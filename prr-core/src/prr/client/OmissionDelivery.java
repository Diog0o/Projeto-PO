package prr.client;

import prr.notifications.Notifications;

import java.util.ArrayList;

public class OmissionDelivery implements DeliveryMethod {

	  public String deliverNotifications(ArrayList<Notifications> notifications) {
		    String res = "\n";
		    for (Notifications noti : notifications) {
		      res += noti.showNotifications() + "\n";
		    }
		    int index = res.length() - 1;
		    if (index >= 0) {
		      return res.substring(0, index);
		    }
		    else {
		      return res;
		    }
		  }
}
