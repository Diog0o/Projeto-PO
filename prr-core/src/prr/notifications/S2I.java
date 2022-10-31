package prr.notifications;

import prr.notifications.Notifications;
import prr.terminals.Terminal;

public class S2I extends Notifications {
	
	private static final long serialVersionUID = 202208091753L;
	
	public S2I(Terminal terminal) {
		super(terminal);
	}
	public String showNotifications() {
		return "S2I" + "|" + get_departureterminal().get_key();
	}
}
