package prr.terminals;

import prr.terminals.Terminal;
import prr.terminals.TerminalStat;

public class Silence extends TerminalStat {
	
	private static final long serialVersionUID = 202208091753L;

	@Override
	public boolean startTextCommunication() {
		return true;
	}

	@Override
	public boolean startInteractiveCommunication() {
		return true;
	}

	@Override
	public boolean receiveTextCommunication() {
		return true;
	}

	@Override
	public boolean receiveInteractiveCommunication() {
		return false;
	}

	@Override
	public boolean updateIsPossible(Terminal terminal) {
		return true;
	}
	@Override
	public String ShowTerminalStat () {
		return "SILENCE";
	}

}
