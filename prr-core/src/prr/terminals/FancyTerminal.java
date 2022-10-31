package prr.terminals;

import java.util.Map;


import prr.client.Client;
import prr.communications.Communication;


public class FancyTerminal extends Terminal {
	
	private static final long serialVersionUID = 202208091753L;
	
	public FancyTerminal(String key, Client owner) {
		super(key,owner);
	}

	

	@Override
	public String ShowTerminalType () {
		return "Fancy";
	}
	

	@Override
	public String ShowTerminal() {
		if (get_friendlist().size() == 0) {
			return "FANCY|" + get_key() + "|" + get_owner().getKey() + "|"+ get_state().ShowTerminalStat() + "|" + this.ShowTerminalPayments()+"|"+this.ShowTerminalDebts();
		}
		else {
			return "FANCY|" + get_key() + "|" + get_owner().getKey() + "|"+ get_state().ShowTerminalStat() + "|" + this.ShowTerminalPayments()+"|"+this.ShowTerminalDebts()+ "|" + this.getFriendKeys();
		}
	}

}
