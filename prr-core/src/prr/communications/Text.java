package prr.communications;

import prr.terminals.Terminal;

public class Text extends Communication {
	
	private static final long serialVersionUID = 202208091753L;
	
	private String _type = "TEXT";
	private final String _text; 
	
	public Text (int id, float cost, Terminal tin, Terminal tout, String text){
		super(id, cost,tin,tout); // super da classe
		_text=text;
	}
	public String get_type() { //getter
		return _type;
}
	public String get_text() {
		return _text;
	}
	public int CharNumber() {
		return _text.length();
	}
	
	@Override
	public String ShowTypeofCommunication() {
		return _type;
	}

	@Override
	public String toString(){
		if (this.get_communicationstate() == true) {
			return "TEXT" + "|" + this.get_id() + "|" + this.get_terminalSender().get_key() + "|" + this.get_terminalReceiver().get_key() + "|" + "0" + "|" + "0" + "|" + this.ShowCommunicationStatus();
		}
		return "TEXT" + "|" + this.get_id() + "|" + this.get_terminalSender().get_key() + "|" + this.get_terminalReceiver().get_key() + "|" + _text.length() + "|" + this.get_cost() + "|" + this.ShowCommunicationStatus();
	}

	@Override
	public float get_minutes(){ //nao vai ser usado
		return 0;
	}

	@Override
	public int get_charnumber(){
		return _text.length();
	}

	@Override
	public void setminutes(float minutes){
	}
}
