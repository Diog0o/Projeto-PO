package prr.terminals;

import prr.terminals.Terminal;
import prr.terminals.TerminalStat;

public class Off extends TerminalStat {
	
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
		return false;
	}

	@Override
	public boolean receiveInteractiveCommunication() {
		return false;
	}

	@Override
	public boolean updateIsPossible(Terminal terminal) {
		if (terminal.get_state().ShowTerminalStat().equals("SILENCE") || terminal.get_state().ShowTerminalStat().equals("IDLE") ) {
			return true;
		}
		return false;
	}
	
	@Override
	public String ShowTerminalStat () {
		return "OFF";
	}

}
