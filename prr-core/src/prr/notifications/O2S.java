package prr.notifications;

import prr.notifications.Notifications;
import prr.terminals.Terminal;

public class O2S extends Notifications {
	
	private static final long serialVersionUID = 202208091753L;
	
	public O2S(Terminal terminal) {
		super(terminal);
	}
	
	public String showNotifications() {
		return "O2S" + "|" + get_departureterminal().get_key();
	}
}
