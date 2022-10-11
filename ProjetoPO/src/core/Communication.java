package core;

public class Communication {
	private final int _id;
	private float _cost;
	private Terminal _terminalIn;
	private Terminal _terminalOut;
	private boolean _communicationState; //Se o mambo tá a decorrer
	private TypesOfComm _type;
	
	public Communication (int id, float cost, Terminal tin, Terminal tout) {
		_id=id;
		_cost=cost;
		_terminalIn = tin;
		_terminalOut = tout;
		
	}

	public float get_cost() {
		return _cost;
	}

	public void set_cost(float _cost) {
		this._cost = _cost;
	}

	public Terminal get_terminalIn() {
		return _terminalIn;
	}

	public void set_terminalIn(Terminal _terminalIn) {
		this._terminalIn = _terminalIn;
	}

	public Terminal get_terminalOut() {
		return _terminalOut;
	}

	public void set_terminalOut(Terminal _terminalOut) {
		this._terminalOut = _terminalOut;
	}

	public int get_id() {
		return _id;
	}
}
