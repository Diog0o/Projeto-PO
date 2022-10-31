package prr.exceptions;

public class TerminalKeyInvalidException extends Exception {
	
	private static final long serialVersionUID = 202208091753L;

    private String _key;

    public TerminalKeyInvalidException(String key) {
        _key = key;
    }

    public String getInvalidKey() {
        return _key;
    }
    
}

