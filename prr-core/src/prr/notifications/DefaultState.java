package prr.notifications;

import prr.terminals.Terminal;

public class DefaultState extends Notifications{
    private static final long serialVersionUID = 202208091753L;

    public DefaultState(Terminal terminal) {
        super(terminal);
    }
    public String showNotifications() {
        return "DEFAULT" + "|" + get_departureterminal().get_key();
    }
}
