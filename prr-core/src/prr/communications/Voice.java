package prr.communications;

import prr.terminals.Terminal;

public class Voice extends Communication {
	
	private static final long serialVersionUID = 202208091753L;
	
	
	private String _type = "VOICE";
	private  float _voiceminutes;
	
	public Voice(int id, float cost, Terminal tin, Terminal tout, float minutes){
		super(id, cost,tin,tout); // super da classe
		_voiceminutes = minutes;
	}
	public String get_type() { //getter
		return _type;
}
	public float get_voiceminutes() {
		return _voiceminutes;
	}
	
	@Override
	public String ShowTypeofCommunication() {
		return _type;
	}

	@Override
	public String toString(){
		if (this.get_communicationstate() == true) {
			return "VOICE" + "|" + this.get_id() + "|" + this.get_terminalSender().get_key() + "|" + this.get_terminalReceiver().get_key() + "|" + "0" + "|" + "0" + "|" + this.ShowCommunicationStatus();
		}
		return "VOICE" + "|" + this.get_id() + "|" + this.get_terminalSender().get_key() + "|" + this.get_terminalReceiver().get_key() + "|" + _voiceminutes + "|" + this.get_cost() + "|" + this.ShowCommunicationStatus();
	}

	@Override
	public float get_minutes(){ //nao vai ser usado
		return _voiceminutes;
	}

	@Override
	public int get_charnumber(){
		return 0;
	}

	@Override
	public void setminutes(float minutes){
		_voiceminutes = minutes;
	}
}
