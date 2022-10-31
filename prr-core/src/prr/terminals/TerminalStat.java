package prr.terminals;

import prr.terminals.Terminal;

import java.io.Serializable;

public abstract class TerminalStat implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 202208091753L;

	public abstract boolean startTextCommunication();
	
	public abstract boolean startInteractiveCommunication();
	
	public abstract boolean receiveTextCommunication();
	
	public abstract boolean receiveInteractiveCommunication();
	
	public abstract boolean updateIsPossible (Terminal terminal);
	
	public abstract String ShowTerminalStat();

}
