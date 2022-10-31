package prr.terminals;

import java.util.Map;

import prr.communications.Communication;
import prr.client.Client;


public class BasicTerminal extends Terminal {
	
	private static final long serialVersionUID = 202208091753L;
	
	public BasicTerminal(String key, Client owner) {
		super(key,owner);
	}

	@Override
	public String ShowTerminalType () {
		return "Basic";
	}

	@Override
	public String ShowTerminal() {
		if (get_friendlist().size() == 0) {
			return "BASIC|" + get_key() + "|" + get_owner().getKey() + "|"+ get_state().ShowTerminalStat() + "|" + this.ShowTerminalPayments()+"|"+this.ShowTerminalDebts();
		}
		else {
			return "BASIC|" + get_key() + "|" + get_owner().getKey() + "|"+ get_state().ShowTerminalStat() + "|" + this.ShowTerminalPayments()+"|"+this.ShowTerminalDebts()+ "|" + this.getFriendKeys();
		}
	}
	
	
}
