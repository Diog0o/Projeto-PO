package prr.terminals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import prr.client.Client;
import prr.communications.Communication;
import prr.notifications.*;


// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */{

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private final String _key;
	private TerminalStat _state;
	private Client _owner; //dono do terminal/cliente
	private TreeMap <Integer, Communication> _terminalpayments = new TreeMap<Integer, Communication>();
	private TreeMap <Integer, Communication> _terminaldebts = new TreeMap<Integer, Communication>();
	private TreeMap <String,Terminal> _friendlist = new TreeMap <String,Terminal>();

	private TerminalStat _previousStat ;
	private ArrayList<Notifications> _pendingnotifications = new ArrayList<>();
	private Communication _ongoingcommunication ;
	private boolean _oncommunication = false;
	public ArrayList<Notifications> get_pendingnotifications() {
		return _pendingnotifications;
	}



	public void set_pendingnotifications(ArrayList<Notifications> _pendingnotifications) {
		this._pendingnotifications = _pendingnotifications;
	}

	public Terminal(String key, Client owner) {
		_key = key;
		_state = new Idle();
		_previousStat= new Idle();
		_owner = owner;
		
	}

	public boolean is_oncommunication() {
		return _oncommunication;
	}

	public void set_oncommunication(boolean _oncommunication) {
		this._oncommunication = _oncommunication;
	}

	public Communication get_ongoingcommunication() {
		return _ongoingcommunication;
	}

	public void set_ongoingcommunication(Communication _ongoingcommunication) {
		this._ongoingcommunication = _ongoingcommunication;
	}

	public TerminalStat get_previousStat() {
		return _previousStat;
	}

	public void set_previousStat(TerminalStat _previousStat) {
		this._previousStat = _previousStat;
	}

	public TerminalStat get_state() {
		return _state;
	}

	public void set_state(TerminalStat _state) {
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
	
	
	public TreeMap<String, Terminal> get_friendlist() {
		return _friendlist;
	}

	public void set_friendlist(TreeMap<String, Terminal> _friendlist) {
		this._friendlist = _friendlist;
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




	public float ShowTerminalPayments() {
		float res =0;
		for (Map.Entry<Integer, Communication> entry : get_terminalpayments().entrySet()) {
			res= res+ entry.getValue().get_cost();
		}
		return res;
	}

	public float ShowTerminalDebts() {
		float res =0;
		for (Map.Entry<Integer, Communication> entry : get_terminaldebts().entrySet()) {
			res= res+ entry.getValue().get_cost();
		}
		return res;
	}

	public boolean AreFriends(Terminal t1, Terminal t2) { //verifica se o terminal t1 está na friendlist do t2
		String key = t1.get_key(); // pega key do t1
		Terminal t= t2.get_friendlist().get(key); //pega friend list do t2 e pega a key do t1, se estiver la sao amigos
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
		for (Map.Entry<Integer,Communication> entry : get_terminaldebts().entrySet()) {
			debts = debts + entry.getValue().get_cost();
		}
		return (paid-debts);
	}

	public String getFriendKeys() {
		String res="";
		for (Map.Entry<String,Terminal> entry : get_friendlist().entrySet()) {
			res= res + entry.getKey() + ",";
		}
		int index = res.length() - 1;
		if (index >= 0) {
			return res.substring(0, index);
		}
		else {
			return res;
		}
	}
	
	public abstract String ShowTerminalType ();

	public abstract String ShowTerminal();
	
	public boolean canEndCurrentCommunication() {
		return false;
  }
	
	public boolean canStartCommunication() { 	
		return false;
 }

	public void updatePendingNotifications(){
		if (_previousStat.ShowTerminalStat().equals("OFF") && _state.ShowTerminalStat().equals("SILENCE")){
			for (int i = 0; i < _pendingnotifications.size(); i++){
				O2S notification = new O2S(_pendingnotifications.get(i).get_departureterminal());
				_pendingnotifications.set(i, notification);
			}
		}
		if (_previousStat.ShowTerminalStat().equals("OFF") && _state.ShowTerminalStat().equals("IDLE")){
			for (int i = 0; i < _pendingnotifications.size(); i++){
				O2I notification = new O2I(_pendingnotifications.get(i).get_departureterminal());
				_pendingnotifications.set(i, notification);
			}
		}
		if (_previousStat.ShowTerminalStat().equals("OCCUPIED") && _state.ShowTerminalStat().equals("IDLE")){
			for (int i = 0; i < _pendingnotifications.size(); i++){
				B2I notification = new B2I(_pendingnotifications.get(i).get_departureterminal());
				_pendingnotifications.set(i, notification);
			}
		}
		if (_previousStat.ShowTerminalStat().equals("SILENCE") && _state.ShowTerminalStat().equals("IDLE")){
			for (int i = 0; i < _pendingnotifications.size(); i++){
				S2I notification = new S2I(_pendingnotifications.get(i).get_departureterminal());
				_pendingnotifications.set(i, notification);
			}
		}
	}


	public void sendpendingnotification(){
		for(int i = 0 ; i < _pendingnotifications.size(); i++ ){
			Notifications n = _pendingnotifications.get(i);
			int endofarray= n.get_departureterminal().get_owner().get_notifications().size();
			n.get_departureterminal().get_owner().get_notifications().add(endofarray,n);
		}
		_pendingnotifications.clear();
	}
}
