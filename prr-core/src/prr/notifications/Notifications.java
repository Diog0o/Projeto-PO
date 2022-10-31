package prr.notifications;

import java.io.Serializable;
import prr.terminals.Terminal;

public abstract class Notifications implements Serializable {

    /**
     * 
     */
	private static final long serialVersionUID = 202208091753L;
	
    private Terminal _departureterminal;

    public Notifications(Terminal terminal) {
        _departureterminal=terminal;
    }
    public Terminal get_departureterminal() {
        return _departureterminal;
    }
    public abstract String showNotifications ();

}