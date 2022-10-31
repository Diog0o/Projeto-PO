package prr.communications;

import java.io.Serializable;
import prr.terminals.Terminal;

public abstract class Communication implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 202208091753L;
	
	private int _id;
	private float _cost;
	private Terminal _terminalSender;
	private Terminal _terminalReceiver;

	private boolean _communicationstate = false; //se esta ou nao a acontecer

	public Communication (int id, float cost, Terminal tsender, Terminal treceiver) {
		_id=id;
		_cost=cost;
		_terminalSender = tsender;
		_terminalReceiver = treceiver;
		
	}

	public boolean get_communicationstate() {
		return _communicationstate;
	}

	public void set_communicationstate(boolean _communicationstate) {
		this._communicationstate = _communicationstate;
	}

	public float get_cost() {
		return _cost;
	}

	public void set_cost(float _cost) {
		this._cost = _cost;
	}

	public Terminal get_terminalSender() {
		return _terminalSender;
	}

	public void set_terminalSender(Terminal _terminalSender) {
		this._terminalSender = _terminalSender;
	}

	public Terminal get_terminalReceiver() {
		return _terminalReceiver;
	}

	public void set_terminalReceiver(Terminal _terminalReceiver) {
		this._terminalReceiver = _terminalReceiver;
	}

	public int get_id() {
		return _id;
	
	}
	public abstract String ShowTypeofCommunication();
	
	public abstract String toString();

	public abstract float get_minutes();

	public abstract int get_charnumber();

	public String ShowCommunicationStatus(){
		if (_communicationstate==true){
			return "ONGOING";
		}
		return "FINISHED";
	}

	public abstract void setminutes(float minutes);
}
