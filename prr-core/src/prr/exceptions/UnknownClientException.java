package prr.exceptions;

public class UnknownClientException extends Exception {
	
	private static final long serialVersionUID = 202208091753L;

    private String _key;

    public UnknownClientException(String key) {
        _key = key;
    }

    public String getUnknownKey() {
        return _key;
    }
    
}
