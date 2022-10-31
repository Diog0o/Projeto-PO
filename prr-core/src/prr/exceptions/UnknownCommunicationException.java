package prr.exceptions;

public class UnknownCommunicationException extends Exception {

    private static final long serialVersionUID = 202208091753L;

    private int _key;

    public UnknownCommunicationException(int key) {
        _key = key;
    }

    public int getUnknownKey() {
        return _key;
    }

}
