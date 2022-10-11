package core;

import java.util.ArrayList;
import java.util.TreeMap;

public class Terminal {
	private final String _key;
	private State _state;
	private Client _owner;
	private TreeMap <Integer, Communication> _terminalpayments = new TreeMap<Integer, Communication>();
	private TreeMap <Integer, Communication> _terminaldebts = new TreeMap<Integer, Communication>();
	private ArrayList <Terminal> friendlist = new ArrayList<Terminal>();

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
	
	public 
	





}

