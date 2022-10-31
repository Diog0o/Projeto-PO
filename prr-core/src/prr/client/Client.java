package prr.client;

import java.io.Serial;
import java.util.ArrayList;


import prr.communications.Communication;
import prr.notifications.Notifications;
import prr.terminals.Terminal;
import java.util.Map;
import java.util.TreeMap;
import java.io.Serializable;


public class Client implements Serializable,Comparable<Client>{ /**
	 *
	 */
	private static final long serialVersionUID = 202208091753L;


	private final String _key;
	private String _name;
	private int _taxid;
	private boolean _hasnotifications= true;
	private TreeMap <String, Terminal> _associatedterminals = new TreeMap<String, Terminal>();
	private Level _level;
	private ArrayList<Notifications> _notifications= new ArrayList<Notifications>();
	private TreeMap<Integer,Communication> _communicationsMade= new TreeMap<Integer,Communication>();
	private TreeMap<Integer,Communication> _communicationsReceived = new TreeMap<>();



	//private DeliveryMethod _delivery;
	public Client (String key, String name, int taxid) {
		_key=key;
		_name=name;
		_taxid=taxid;
		_level= new Normal(this);

	}



	public ArrayList<Notifications> get_notifications() {
		return _notifications;
	}



	public void set_notifications(ArrayList<Notifications> _notifications) {
		this._notifications = _notifications;
	}

	public TreeMap<Integer, Communication> get_communicationsMade() {
		return _communicationsMade;
	}

	public void set_communicationsMade(TreeMap<Integer, Communication> _communicationsMade) {
		this._communicationsMade = _communicationsMade;
	}

	public TreeMap<Integer, Communication> get_communicationsReceived() {
		return _communicationsReceived;
	}

	public void set_communicationsReceived(TreeMap<Integer, Communication> _communicationsReceived) {
		this._communicationsReceived = _communicationsReceived;
	}

	public String getKey() {
		return _key;
	}


	public String getName() {
		return _name;
	}


	public void setName(String name) {
		_name = name;
	}


	public int getTaxid() {
		return _taxid;
	}


	public void setTaxid(int taxid) {
		_taxid = taxid;
	}


	public boolean isNotifications() {
		return _hasnotifications;
	}


	public void setNotifications(boolean notifications) {
		_hasnotifications = notifications;
	}

	public String getLevelName () {
		return _level.ShowLevel();
	}

	public Level get_level() {
		return _level;
	}

	public TreeMap<String, Terminal> get_associatedterminals() {
		return _associatedterminals;
	}

	public void set_associatedterminals(TreeMap<String, Terminal> _associatedterminals) {
		this._associatedterminals = _associatedterminals;
	}

	public void set_level(Level _level) {
		this._level = _level;
	}


	public float ClientBalance() {
		float res = 0;
		for (Map.Entry<String, Terminal> entry: _associatedterminals.entrySet()) {
			res= res + entry.getValue().TerminalBalance();
		}
		return res;
	}


	public int ConsecutiveVideo() {
		int res=0;
		boolean check = true;
		for (Map.Entry<Integer, Communication> entry: _communicationsMade.entrySet()) {
			while (check == true);
				if (entry.getValue().ShowTypeofCommunication().equals("VIDEO") ){
					res= res +1;
				}
				else {
					check = false;
				}
		}
		return res;
	}

	public int ConsecutiveText() {
		int res=0;
		boolean check = true;
		for (Map.Entry<Integer, Communication> entry: _communicationsMade.entrySet()) {
			while (check == true);
				if (entry.getValue().ShowTypeofCommunication() == "TEXT"){
					res= res +1;
				}
				else {
					check = false;
				}
		}
		return res;
	}

	public int ConsecutiveVoice() {
		int res=0;
		boolean check = true;
		for (Map.Entry<Integer, Communication> entry: _communicationsMade.entrySet()) {
			while (check == true);
				if (entry.getValue().ShowTypeofCommunication() == "VOICE"){
					res= res + 1;
				}
				else {
					check = false;
				}
		}
		return res;
	}

	public int NumberOfTerminals () {
		return _associatedterminals.size();
		}


	public float Payments(){
		float res=0;
		for (Map.Entry<String,Terminal> entry: _associatedterminals.entrySet()){ //entry = (K,V), KEY string Value Terminal
			for (Map.Entry<Integer,Communication> entry2 : entry.getValue().get_terminalpayments().entrySet()){ //entry2 is
				res=res+entry2.getValue().get_cost();
			}
		}
		return res;
	}

	public float Debts() {
		float res = 0;
		for (Map.Entry<String, Terminal> entry: _associatedterminals.entrySet()) { // runs the associated terminals and pick a _associatedterminals
			for (Map.Entry<Integer, Communication> entry2: entry.getValue().get_terminaldebts().entrySet()) { //pic (K,V), value
				res= res + entry2.getValue().get_cost();
			}
		}
		return res;
	}

	public String YesOrNoNotifications () {
		if (_hasnotifications ==true){
			return "YES";
		}
		else {
			return "NO";
		}
	}


    public void ClearNotifications() {
        _notifications.clear();
    }

    public String showClientNotifications(DeliveryMethod delivery) {
        String out = delivery.deliverNotifications(_notifications);
        ClearNotifications();
        return out;
    }

	public String ShowClient () {
		return "Client|" + _key + "|"+ _name + "|"+ _taxid + "|" + _level.ShowLevel() +"|" + this.YesOrNoNotifications() + "|" + this.NumberOfTerminals() + "|" + this.Payments()+ "|" + this.Debts();
	}
	@Override
	public int compareTo(Client client){
		return _key - client.getKey();
	}

	List clients = new ArrayList<Client>(_client.values())
			Collections.sort(clients)

	
}

