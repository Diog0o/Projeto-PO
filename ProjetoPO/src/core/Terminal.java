package core;

import java.util.Map;
import java.util.TreeMap;

public class Terminal {
	private final String _key;
	private State _state;
	private Client _owner;
	private TreeMap <Integer, Communication> _terminalpayments = new TreeMap<Integer, Communication>();
	private TreeMap <Integer, Communication> _terminaldebts = new TreeMap<Integer, Communication>();
	private TreeMap <String,Terminal> _friendlist = new TreeMap <String,Terminal>();

	public Terminal(String key, State state, Client owner) {
		_key = key;
		_state = new State();
		_owner = owner;
		
	}

	public State get_state() {
		return _state;
	}

	public void set_state(State _state) {
		this._state = _state;
	}

	public Client get_owner() {
		return _owner;
	}

	public void set_owner(Client _owner) {
		this._owner = _owner;
	}

	public String get_key() {
		return _key;
	}
	
	
	public String ShowTerminalPayments () {
		String res ="";
		for (Map.Entry<Integer, Communication> entry : get_terminalpayments().entrySet()) {
			res= res+ entry.getKey() + "\n";
		}
		return res;
	}	
	
	public String ShowTerminalDebts () {
		String res ="";
		for (Map.Entry<Integer, Communication> entry : _terminaldebts.entrySet()) {
			res= res+ entry.getKey() + "\n";
		}
		return res;
	}
	
	
	public boolean AreFriends(Terminal t1, Terminal t2) { //ver
		String key = t1.get_key(); // pega key do t1
		Terminal t= t2._friendlist.get(key); //poe 
		if (t != null) {
			return true;
		}
		else {
			return false;
		}
	}
	public float TerminalBalance() {
		float paid = 0;
		float debts=0;
		for (Map.Entry<Integer,Communication> entry : get_terminalpayments().entrySet()) { 
			paid=paid + entry.getValue().get_cost();
		}
		for (Map.Entry<Integer,Communication> entry : _terminaldebts.entrySet()) {
			debts = debts + entry.getValue().get_cost();
	} 
		return (paid-debts);
	}

	public TreeMap <Integer, Communication> get_terminalpayments() {
		return _terminalpayments;
	}

	public void set_terminalpayments(TreeMap <Integer, Communication> _terminalpayments) {
		this._terminalpayments = _terminalpayments;
	}
	
	public TreeMap <Integer, Communication> get_terminaldebts() {
		return _terminaldebts;
	}

	public void set_terminaldebts(TreeMap <Integer, Communication> _terminaldebts) {
		this._terminaldebts = _terminaldebts;
	}
	
}	

