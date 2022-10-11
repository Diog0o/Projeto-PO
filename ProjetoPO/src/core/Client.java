package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Client { // Falta dar extend do comparable 
	private final String _key;
	private String _name;
	private int _taxid;
	private boolean _notifications= true;
	private TreeMap <String, Terminal> _associatedterminals = new TreeMap<String, Terminal>();
	private Level _level;
	
	
	public Client (String key, String name, int taxid) {
		_key=key;
		_name=name;
		_taxid=taxid;
		_level= new NormalClient(this,0);
		
		
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
		return _notifications;
	}


	public void setNotifications(boolean notifications) {
		_notifications = notifications;
	}
	
	public String getLevelName () {
		return _level.getName();
	}
	
	public void addassociatedterminals (Terminal terminal) {
		_associatedterminals.put(terminal.get_key(), terminal);
		
	}
	
	public String ShowAssociatedTerminals () {
		String res ="";
		for (Map.Entry<String, Terminal> entry : _associatedterminals.entrySet()) {
			res= res+ entry.getKey() + "\n";
		}
		return res;
	}
	
	public void ChangeNotification () {
		if (_notifications=true){
			_notifications=false;
		}	
		else {
			_notifications=true;
		}
	}
	
	
		
}

