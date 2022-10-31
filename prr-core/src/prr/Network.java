package prr;
import prr.client.Client;
import prr.client.DeliveryMethod;
import prr.client.Normal;
import prr.client.OmissionDelivery;
import prr.communications.Text;
import prr.communications.Video;
import prr.communications.Voice;
import prr.exceptions.*;
import prr.notifications.DefaultState;
import prr.terminals.*;
import prr.communications.Communication;



import java.io.Serializable;



import java.text.Collator;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

import prr.terminals.BasicTerminal;
import prr.terminals.FancyTerminal;


// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

	/**
	 * Serial number for serialization.
	 */
	private static final long serialVersionUID = 202208091753L;


//-------------------------------------------------------------------------------------

	/**
	 * Class CollatorWrapper implements a collator that can be serialized.
	 */

	class CollatorWrapper implements Comparator<String>, Serializable {
		private static final long serialVersionUID = 202208091753L;

		private transient Collator _collator = Collator.getInstance(Locale.getDefault());

		private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
			ois.defaultReadObject();
			_collator = Collator.getInstance(Locale.getDefault());
		}

		@Override
		public int compare(String s1, String s2) {
			return _collator.compare(s1, s2);
		}
	}

//-----------------------------------------------------------------------------------------	  
	/**
	 * Clients
	 */
	private TreeMap<String, Client> _clients = new TreeMap<String, Client>(new CollatorWrapper());

	/**
	 * Terminals
	 */
	private TreeMap<String, Terminal> _terminals = new TreeMap<String, Terminal>(new CollatorWrapper());

	/**
	 * Communications
	 */
	private TreeMap<Integer, Communication> _communications = new TreeMap<Integer, Communication>();

	/**
	 * Number of communications registered
	 */
	private int _communicationid = 0;


//CLIENTS-----------------------------------------------------------------------------------------------

	/**
	 * Registers a client to the network.
	 *
	 * @param key   client key
	 * @param name  client name
	 * @param taxid client taxId
	 * @throws DuplicateClientException
	 */

	public void registerClient(String key, String name, int taxid) throws DuplicateClientException {
		Client c = _clients.get(key);
		if (c == null) {
			c = new Client(key, name, taxid);
			_clients.put(key, c); // put client on clients tree map (key,client)
		} else {
			throw new DuplicateClientException(key);
		}
	}

	/**
	 * Shows all clients registered in the network.
	 *
	 * @return a string that contains all clients registered in the network.
	 */


	public String ShowAllClients() {
		String res = "";
		for (Map.Entry<String, Client> entry : _clients.entrySet()) { //gets key and value of client treemap
			res = res + entry.getValue().ShowClient() + "\n";
		}
		int index = res.length() - 1;
		if (index >= 0) {
			return res.substring(0, index);
		} else {
			return res;
		}
	}


	/**
	 * Shows a single client registered in the network given a key.
	 *
	 * @param clientKey client key
	 * @return a string that contains a single client registered in the network given a key.
	 * @throws UnknownClientException
	 */
	public String showClient(String clientKey) throws UnknownClientException {
		Client c = _clients.get(clientKey);
		if (c == null) {
			throw new UnknownClientException(clientKey);
		} else {
			DeliveryMethod delivery = new OmissionDelivery();
			return c.ShowClient() + c.showClientNotifications(delivery);
		}
	}

	public void activateNotifications(String clientKey) throws UnknownClientException {
		Client c = _clients.get(clientKey);
		if (c != null) {
			c.setNotifications(true);
		} else {
			throw new UnknownClientException(clientKey);
		}
	}

	public void disableNotifications(String clientKey) throws UnknownClientException {
		Client c = _clients.get(clientKey);
		if (c != null) {
			c.setNotifications(false);
		} else {
			throw new UnknownClientException(clientKey);
		}
	}

	public String ShowPaymentsandDebts(String clientKey) throws UnknownClientException {
		Client c = _clients.get(clientKey);
		if (c != null) {
			return "Pagamentos:" + _clients.get(clientKey).Payments() + "| Dívidas: " + _clients.get(clientKey).Debts();
		} else {
			throw new UnknownClientException(clientKey);
		}
	}

//GESTÃO DE TERMINAIS----------------------------------------------------------------------------------------------


	/**
	 * Registers a terminal to the network.
	 *
	 * @param key       terminal key
	 * @param type      either FANCY or BASIC
	 * @param clientKey client key associated to the terminal
	 * @param state     state of the Terminal (ON,OFF,SILENCE)
	 * @throws DuplicateTerminalException
	 * @throws TerminalKeyInvalidException
	 */
	public void registerTerminal(String key, String type, String clientKey, String state) throws DuplicateTerminalException, TerminalKeyInvalidException {
		if (key.length() == 6 && key.matches("(\\d{6})")) {
			Terminal t = _terminals.get(key);
			if (t == null) {
				if (type.equals("BASIC")) {
					t = new BasicTerminal(key, _clients.get(clientKey));
					if (state.equals("OFF")) {
						t.set_state(new Off());
					}
					if (state.equals("SILENCE")) {
						t.set_state(new Silence());
					}
					_terminals.put(key, t);
				} else if (type.equals("FANCY")) {
					t = new FancyTerminal(key, _clients.get(clientKey));
					if (state.equals("OFF")) {
						t.set_state(new Off());
					}
					if (state.equals("SILENCE")) {
						t.set_state(new Silence());
					}
					_terminals.put(key, t);
				}
			} else {
				throw new DuplicateTerminalException(key);
			}
		} else {
			throw new TerminalKeyInvalidException(key);
		}

	}

	/**
	 * Shows all terminals registered in the network.
	 *
	 * @return a string that contains all terminals registered in the network.
	 */
	public String showAllTerminals() {
		String res = "";
		for (Map.Entry<String, Terminal> entry : _terminals.entrySet()) {
			res += entry.getValue().ShowTerminal() + "\n";
		}
		int index = res.length() - 1;
		if (index >= 0) {
			return res.substring(0, index);
		} else {
			return res;
		}
	}

	/**
	 * Add a terminal2 to the friendlist of terminal 1.
	 *
	 * @param terminalKey1 terminal key
	 * @param terminalKey2 terminal key
	 * @throws UnknownTerminalException
	 */
	public void addFriend(String terminalKey1, String terminalKey2) throws UnknownTerminalException { //Adiciona o terminal da key2 a lista de friends do terminal com a key1
		Terminal t1 = _terminals.get(terminalKey1);
		Terminal t2 = _terminals.get(terminalKey2);
		if (t1 == null) {
			throw new UnknownTerminalException(terminalKey1);
		}
		if (t2 != null) {
			throw new UnknownTerminalException(terminalKey2);
		}
		if (t1.get_friendlist().containsKey(terminalKey2) == false) {
			t1.get_friendlist().put(terminalKey2, t2);
		}
	}

	public Terminal getTerminal(String terminalKey) throws TerminalKeyInvalidException, UnknownTerminalException {
		if (terminalKey.length() == 6 && terminalKey.matches("(\\d{6})")) {
			Terminal t = _terminals.get(terminalKey);
			if (t != null) {
				return t;

			} else {
				throw new UnknownTerminalException(terminalKey);
			}

		} else {
			throw new TerminalKeyInvalidException(terminalKey);
		}
	}


//CONSULTAS------------------------------------------------------------------------------------

	/**
	 * Shows all terminals without activity registered in the network.
	 *
	 * @return a string that contains all terminals without activity registered in the network.
	 */

	public String showTerminalsWithoutActivity() {
		String res = "";
		for (Map.Entry<String, Terminal> entry : _terminals.entrySet()) {
			if (entry.getValue().get_terminalpayments().size() == 0 && entry.getValue().get_terminaldebts().size() == 0) {
				res += entry.getValue().ShowTerminal() + "\n";
			}
		}
		int index = res.length() - 1;
		if (index >= 0) {
			return res.substring(0, index);
		} else {
			return res;
		}
	}

	public String ShowCommunicationsMade(String clientKey) throws UnknownClientException {
		Client c = _clients.get(clientKey);
		if (c == null) {
			throw new UnknownClientException(clientKey);
		} else {
			String res = "";
			for (Map.Entry<Integer, Communication> entry : c.get_communicationsMade().entrySet()) {
				res = res + entry.getValue().toString() + "\n";
			}
			int index = res.length() - 1;
			if (index >= 0) {
				return res.substring(0, index);
			} else {
				return res;
			}
		}
	}

	public String ShowCommunicationsReceived(String clientKey) throws UnknownClientException {
		Client c = _clients.get(clientKey);
		if (c == null) {
			throw new UnknownClientException(clientKey);
		} else {
			String res = "";
			for (Map.Entry<Integer, Communication> entry : c.get_communicationsReceived().entrySet()) {
				res = res + entry.getValue().toString() + "\n";
			}
			int index = res.length() - 1;
			if (index >= 0) {
				return res.substring(0, index);
			} else {
				return res;
			}
		}

	}


	public String ShowClientsWithoutDebts() {
		String res = "";
		for (Map.Entry<String, Client> entry : _clients.entrySet()) {
			for (Map.Entry<String, Terminal> terminalEntry : entry.getValue().get_associatedterminals().entrySet()) {
				if (terminalEntry.getValue().get_terminaldebts().size() == 0) {
					res = res + entry.getValue().ShowClient() + "\n";
				}
			}
		}
		int index = res.length() - 1;
		if (index >= 0) {
			return res.substring(0, index);
		} else {
			return res;
		}
	}

	//FALTA SABER ORDENAR ISTO E FAZER ISTO
	public String ShowClientsWithDebts() {
		ArrayList<Client> aux = new ArrayList<>();
		for (Map.Entry<String, Client> entry : _clients.entrySet()) {
			if (entry.getValue().Debts() != 0) {
				aux.add(entry.getValue());
			}
		}
		return null;
	}

	public String ShowTerminalsWithPositiveBalance() {
		String res = "";
		for (Map.Entry<String, Terminal> entry : _terminals.entrySet()) {
			if (entry.getValue().TerminalBalance() > 0) {
				res = res + entry.getValue().ShowTerminal();
			}
		}
		int index = res.length() - 1;
		if (index >= 0) {
			return res.substring(0, index);
		} else {
			return res;
		}
	}

//CONSOLA DE UM TERMINAL-----------------------------------------------------------------------------------------------

	public void TurnOnTerminal(String terminalKey) throws TerminalKeyInvalidException, UnknownTerminalException {
		if (terminalKey.length() == 6 && terminalKey.matches("(\\d{6})")) {
			Terminal t = _terminals.get(terminalKey);
			if (t != null) {
				if (!t.get_state().ShowTerminalStat().equals("OCCUPIED") && !t.get_state().ShowTerminalStat().equals("IDLE")) {
					t.set_previousStat(t.get_state());
					t.set_state(new Idle());
					t.updatePendingNotifications();
					t.sendpendingnotification();
					for (int i = 0; i < t.get_pendingnotifications().size(); i++) {
						int endOfArray = t.get_pendingnotifications().size();
						t.get_pendingnotifications().get(i).get_departureterminal().get_owner().get_notifications().add(endOfArray, t.get_pendingnotifications().get(i));
					}
					t.get_pendingnotifications().clear();
				}
			} else {
				throw new UnknownTerminalException(terminalKey);
			}
		} else {
			throw new TerminalKeyInvalidException(terminalKey);
		}
	}

	public void TurnOffTerminal(String terminalKey) throws TerminalKeyInvalidException, UnknownTerminalException {
		if (terminalKey.length() == 6 && terminalKey.matches("(\\d{6})")) {
			Terminal t = _terminals.get(terminalKey);
			if (t != null) {
				if (!t.get_state().ShowTerminalStat().equals("OCCUPIED") && !t.get_state().ShowTerminalStat().equals("OFF")) {
					t.set_previousStat(t.get_state());
					t.set_state(new Off());
				}
			} else {
				throw new UnknownTerminalException(terminalKey);
			}
		} else {
			throw new TerminalKeyInvalidException(terminalKey);
		}
	}

	public void TurnSilentTerminal(String terminalKey) throws TerminalKeyInvalidException, UnknownTerminalException {
		if (terminalKey.length() == 6 && terminalKey.matches("(\\d{6})")) {
			Terminal t = _terminals.get(terminalKey);
			if (t != null) {
				if (!t.get_state().ShowTerminalStat().equals("OCCUPIED") && !t.get_state().ShowTerminalStat().equals("SILENCE")) {
					t.set_previousStat(t.get_state());
					t.set_state(new Silence());
					t.updatePendingNotifications();
					t.sendpendingnotification();
					for (int i = 0; i < t.get_pendingnotifications().size(); i++) {
						int endOfArray = t.get_pendingnotifications().size();
						t.get_pendingnotifications().get(i).get_departureterminal().get_owner().get_notifications().add(endOfArray, t.get_pendingnotifications().get(i));
					}
					t.get_pendingnotifications().clear();
				}
			} else {
				throw new UnknownTerminalException(terminalKey);
			}
		} else {
			throw new TerminalKeyInvalidException(terminalKey);
		}
	}

	public void AddFriend(String terminalKey, String terminalKeytoadd) throws TerminalKeyInvalidException, UnknownTerminalException {
		if (terminalKey.length() == 6 && terminalKey.matches("(\\d{6})")) {
			Terminal t = _terminals.get(terminalKey);
			if (t != null) {
				if (terminalKeytoadd.length() == 6 && terminalKeytoadd.matches("(\\d{6})")) {
					Terminal t2 = _terminals.get(terminalKeytoadd);
					if (t2 != null) {
						if (!t.get_friendlist().containsKey(terminalKeytoadd)) {
							t.get_friendlist().put(terminalKeytoadd, t2);
						}
					} else {
						throw new UnknownTerminalException(terminalKeytoadd);
					}
				} else {
					throw new TerminalKeyInvalidException(terminalKeytoadd);
				}
			} else {
				throw new UnknownTerminalException(terminalKey);
			}
		} else {
			throw new TerminalKeyInvalidException(terminalKey);
		}
	}

	public void RemoveFriend(String terminalKey, String terminalKeytoremove) throws TerminalKeyInvalidException, UnknownTerminalException {
		if (terminalKey.length() == 6 && terminalKey.matches("(\\d{6})")) {
			Terminal t = _terminals.get(terminalKey);
			if (t != null) {
				if (terminalKeytoremove.length() == 6 && terminalKeytoremove.matches("(\\d{6})")) {
					Terminal t2 = _terminals.get(terminalKeytoremove);
					if (t2 != null) {
						if (t.get_friendlist().containsKey(terminalKeytoremove)) {
							t.get_friendlist().remove(terminalKeytoremove);
						}
					} else {
						throw new UnknownTerminalException(terminalKeytoremove);
					}
				} else {
					throw new TerminalKeyInvalidException(terminalKeytoremove);
				}
			} else {
				throw new UnknownTerminalException(terminalKey);
			}
		} else {
			throw new TerminalKeyInvalidException(terminalKey);
		}
	}

	public void MakePayment(String terminalKey, int id) throws UnknownTerminalException, TerminalKeyInvalidException, UnknownCommunicationException {
		if (terminalKey.length() == 6 && terminalKey.matches("(\\d{6})")) {
			Terminal t = _terminals.get(terminalKey);
			if (t != null) {
				Communication c = _communications.get(id);
				if (c != null) {
					if (t.get_terminaldebts().containsKey(id)) {
						t.get_terminalpayments().put(id, c);
						t.get_terminaldebts().remove(id);
						t.get_owner().get_level().updateLevel();
					} else {
						throw new UnknownCommunicationException(id);
					}
				} else {
					throw new UnknownCommunicationException(id);
				}
			} else {
				throw new UnknownTerminalException(terminalKey);
			}
		} else {
			throw new TerminalKeyInvalidException(terminalKey);
		}
	}

	public String ShowPaymentsandDebtsTerminals(String terminalKey) throws TerminalKeyInvalidException, UnknownTerminalException {
		if (terminalKey.length() == 6 && terminalKey.matches("(\\d{6})")) {
			Terminal t = _terminals.get(terminalKey);
			if (t != null) {
				return "Pagamentos:" + t.ShowTerminalPayments() + "|Dividas:" + t.ShowTerminalDebts();
			} else {
				throw new UnknownTerminalException(terminalKey);
			}
		} else {
			throw new TerminalKeyInvalidException(terminalKey);
		}
	}

	public void SendText(String terminalKeysender, String terminalKeyreceiver, String text) throws TerminalKeyInvalidException, UnknownTerminalException, CantStartCommunicationException {
		if (terminalKeysender.length() == 6 && terminalKeysender.matches("(\\d{6})")) {
			Terminal t = _terminals.get(terminalKeysender);
			if (t != null) {
				if (terminalKeyreceiver.length() == 6 && terminalKeyreceiver.matches("(\\d{6})")) {
					Terminal t2 = _terminals.get(terminalKeyreceiver);
					if (t2 != null) {
						if (t.get_state().startTextCommunication()) {
							if (t2.get_state().receiveTextCommunication()) {
								Communication c = new Text(_communicationid++, 0, t, t2, text);
								c.set_cost(t.get_owner().get_level().cost(c));
								t.get_owner().get_communicationsMade().put(c.get_id(), c);
								t.get_terminaldebts().put(c.get_id(), c);
								t2.get_owner().get_communicationsReceived().put(c.get_id(), c);
								t.get_owner().get_level().updateLevel();
							} else {
								if (t.get_owner().isNotifications()) {
									int endofarray = t2.get_pendingnotifications().size();
									DefaultState defalt = new DefaultState(t);
									t2.get_pendingnotifications().add(endofarray, defalt);
								}
							}
						} else {
							throw new CantStartCommunicationException();
						}
					} else {
						throw new UnknownTerminalException(terminalKeyreceiver);
					}
				} else {
					throw new TerminalKeyInvalidException(terminalKeyreceiver);
				}
			} else {
				throw new UnknownTerminalException(terminalKeysender);
			}
		} else {
			throw new TerminalKeyInvalidException(terminalKeysender);
		}
	}

	public void StartInteractiveCommunication(String terminalKeysender, String terminalKeyreceiver, String videoorvoice) throws TerminalKeyInvalidException, UnknownTerminalException, CantStartCommunicationException {
		if (terminalKeysender.length() == 6 && terminalKeysender.matches("(\\d{6})")) {
			Terminal t = _terminals.get(terminalKeysender);
			if (t != null) {
				if (terminalKeyreceiver.length() == 6 && terminalKeyreceiver.matches("(\\d{6})")) {
					Terminal t2 = _terminals.get(terminalKeyreceiver);
					if (t2 != null) {
						if (t.get_state().startInteractiveCommunication()) {
							if (t2.get_state().receiveTextCommunication()) {
								if (videoorvoice.equals("VOICE") && !t.is_oncommunication()) {
									Communication c = new Voice(_communicationid++, 0, t, t2, 0);
									t.set_state(new Occupied());
									t.set_oncommunication(true);
									t.set_ongoingcommunication(c);
									c.set_communicationstate(true);
								}
								if (t2.get_state().receiveTextCommunication()) {
									if (videoorvoice.equals("VIDEO") && !t.is_oncommunication() && t.ShowTerminalType().equals("Fancy")) {
										Communication c = new Video(_communicationid++, 0, t, t2, 0);
										t.set_state(new Occupied());
										t.set_oncommunication(true);
										t.set_ongoingcommunication(c);
										c.set_communicationstate(true);
									}
								} else {
									if (t.get_owner().isNotifications()) {
										int endofarray = t2.get_pendingnotifications().size();
										DefaultState defalt = new DefaultState(t);
										t2.get_pendingnotifications().add(endofarray, defalt);
									}
								}
							} else {
								throw new CantStartCommunicationException();
							}
						} else {
							throw new UnknownTerminalException(terminalKeyreceiver);
						}
					} else {
						throw new TerminalKeyInvalidException(terminalKeyreceiver);
					}
				} else {
					throw new UnknownTerminalException(terminalKeysender);
				}
			} else {
				throw new TerminalKeyInvalidException(terminalKeysender);
			}
		}
	}

	public void EndInteractiveCommunication(String terminalKeysender, float tempo) throws TerminalKeyInvalidException, UnknownTerminalException, CantStartCommunicationException {
		if (terminalKeysender.length() == 6 && terminalKeysender.matches("(\\d{6})")) {
			Terminal t = _terminals.get(terminalKeysender);
			if (t != null) {
				if (t.is_oncommunication()) {
					t.set_oncommunication(false);
					t.get_ongoingcommunication().set_communicationstate(false);
					t.set_state(t.get_previousStat());
					t.set_previousStat(new Occupied());
					t.updatePendingNotifications();
					t.sendpendingnotification();
					t.get_ongoingcommunication().setminutes(tempo);
					float cost = t.get_owner().get_level().cost(t.get_ongoingcommunication());
					t.get_ongoingcommunication().set_cost(cost);
					t.get_terminaldebts().put(t.get_ongoingcommunication().get_id(), t.get_ongoingcommunication());
					t.get_owner().get_communicationsMade().put(t.get_ongoingcommunication().get_id(), t.get_ongoingcommunication());
					t.get_ongoingcommunication().get_terminalReceiver().get_owner().get_communicationsReceived().put(t.get_ongoingcommunication().get_id(), t.get_ongoingcommunication());
					t.get_owner().get_level().updateLevel();
				} else {
					throw new CantStartCommunicationException();
				}
			} else {
				throw new UnknownTerminalException(terminalKeysender);
			}
		} else {
			throw new TerminalKeyInvalidException(terminalKeysender);
		}
	}

	public String ShowOnGoingCommunication(String terminalkey) throws TerminalKeyInvalidException, UnknownTerminalException {

		if (terminalkey.length() == 6 && terminalkey.matches("(\\d{6})")) {
			Terminal t = _terminals.get(terminalkey);
			if (t != null) {
				return t.get_ongoingcommunication().toString();
			} else {
				throw new UnknownTerminalException(terminalkey);
			}
		} else {
			throw new TerminalKeyInvalidException(terminalkey);
		}
	}






//-----------------------------------------------------------------------------------------------------------------------------------------
		/**
		 * @param filename filename to be loaded.
		 * @throws IOException
		 * @throws UnrecognizedEntryException
		 * @throws DuplicateClientException
		 * @throws DuplicateTerminalException
		 * @throws UnknownTerminalException
		 */
		void importFile (String filename) throws
		UnrecognizedEntryException, IOException, DuplicateClientException, DuplicateTerminalException, UnknownTerminalException /* FIXME maybe other exceptions */
		{
			//FIXME implement method
			try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
				String textLine;

				while ((textLine = reader.readLine()) != null) {
					String[] inputs = textLine.split("\\|");

					if (inputs[0].equals("CLIENT")) {
						registerClient(inputs[1], inputs[2], Integer.parseInt(inputs[3]));
					} else if (inputs[0].equals("BASIC")) {
						registerTerminal(inputs[1], inputs[0], inputs[2], inputs[3]);
					} else if (inputs[0].equals("FANCY")) {
						registerTerminal(inputs[1], inputs[0], inputs[2], inputs[3]);
					} else if (inputs[0].equals("FRIENDS")) {
						String[] friends = inputs[2].split(",");
						for (String terminalkey : friends) {
							addFriend(inputs[1], terminalkey);
						}

					} else {
						throw new UnrecognizedEntryException(inputs[0]);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DuplicateClientException e) {
				e.printStackTrace();
			} catch (DuplicateTerminalException e) {
				e.printStackTrace();
			} catch (TerminalKeyInvalidException e) {
				e.printStackTrace();
			} catch (UnrecognizedEntryException e) {
				e.printStackTrace();
			}
		}
	}



