package prr.terminals;

import prr.terminals.Terminal;
import prr.terminals.TerminalStat;

public class Occupied extends TerminalStat {
	
	private static final long serialVersionUID = 202208091753L;

	@Override
	public boolean startTextCommunication() {
		return false;
	}

	@Override
	public boolean startInteractiveCommunication() {
		return false;
	}

	@Override
	public boolean receiveTextCommunication() {
		return true;
	}

	@Override
	public boolean receiveInteractiveCommunication() {
		return true;
	}

	@Override
	public boolean updateIsPossible(Terminal terminal) {
		if (terminal.get_state().ShowTerminalStat() == "SILENCE" || terminal.get_state().ShowTerminalStat() == "IDLE" ) {
			return true;
		}
		return false;
	}
	
	@Override
	public String ShowTerminalStat () {
		return "OCCUPIED";
	}

}
