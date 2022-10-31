package prr.notifications;

import prr.notifications.Notifications;
import prr.terminals.Terminal;

public class O2I extends Notifications {
	
	private static final long serialVersionUID = 202208091753L;
	
	public O2I(Terminal terminal) {
		super(terminal);
	}
	public String showNotifications() {
		return "O2I" + "|" + get_departureterminal().get_key();
	}
}

