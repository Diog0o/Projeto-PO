package prr;
import prr.terminals.Terminal;

import java.io.IOException;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import prr.exceptions.DuplicateClientException;
import prr.exceptions.DuplicateTerminalException;
import prr.exceptions.ImportFileException;
import prr.exceptions.TerminalKeyInvalidException;
import prr.exceptions.MissingFileAssociationException;
import prr.exceptions.UnavailableFileException;
import prr.exceptions.UnknownClientException;
import prr.exceptions.UnknownTerminalException;
import prr.exceptions.UnrecognizedEntryException;

//FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Manage access to network and implement load/save operations.
 */
public class NetworkManager {

	/** The network itself. */
	private Network _network = new Network();
        //FIXME  addmore fields if needed
	private String _filename ="";

        public Network getNetwork() {
		return _network;
	}
        
        public void registerClient(String key, String name, int taxid) throws DuplicateClientException {
        	_network.registerClient(key, name, taxid);
        }
        
        public String showAllClients() {
        	return _network.showAllClients();
        }
        
        public String showClient (String clientKey) throws UnknownClientException {
        	return _network.showClient(clientKey);
        }
        
        public void registerTerminal(String key, String type, String clientKey, String state) throws DuplicateTerminalException,TerminalKeyInvalidException  {
        	_network.registerTerminal(key, type, clientKey, state);
        }
        
        public String showAllTerminals() {
        	return _network.showAllTerminals();
        }
        
        public void addFriend (String terminalKey1, String terminalKey2) throws UnknownTerminalException { 
        	_network.addFriend(terminalKey1, terminalKey2);
        }
        
        public String showTerminalsWithoutActivity() {
        	return _network.showTerminalsWithoutActivity();
        }
	/**
	 * @param filename name of the file containing the serialized application's state
         *        to load.
	 * @throws UnavailableFileException if the specified file does not exist or there is
         *         an error while processing this file.
	 */
	public void load(String filename) throws UnavailableFileException, IOException, ClassNotFoundException, FileNotFoundException {
		//FIXME implement serialization method
		 ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
		    _network = (Network) in.readObject();
		    in.close();
		    _filename = filename;
	}

	/**
         * Saves the serialized application's state into the file associated to the current network.
         *
	 * @throws FileNotFoundException if for some reason the file cannot be created or opened. 
	 * @throws MissingFileAssociationException if the current network does not have a file.
	 * @throws IOException if there is some error while serializing the state of the network to disk.
	 */
	public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
		//FIXME implement serialization method
	    if (!_filename.equals("")) {
	        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)));
	        out.writeObject(_network);
	        out.close();
	      }
	      else {
	        throw new MissingFileAssociationException();
	      }
	}

	/**
         * Saves the serialized application's state into the specified file. The current network is
         * associated to this file.
         *
	 * @param filename the name of the file.
	 * @throws FileNotFoundException if for some reason the file cannot be created or opened.
	 * @throws MissingFileAssociationException if the current network does not have a file.
	 * @throws IOException if there is some error while serializing the state of the network to disk.
	 */
	public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
		//FIXME implement serialization method
		  _filename = filename;
		  save();
		}

	/**
	 * Read text input file and create domain entities..
	 * 
	 * @param filename name of the text input file
	 * @throws ImportFileException
	 * @throws UnknownTerminalException 
	 */
	public void importFile(String filename) throws ImportFileException, UnknownTerminalException {
		try {
			_network.importFile(filename);
		} catch (IOException | UnrecognizedEntryException | DuplicateClientException | DuplicateTerminalException /* FIXME maybe other exceptions */ e) {
			throw new ImportFileException(filename, e);
    }
	}

}
