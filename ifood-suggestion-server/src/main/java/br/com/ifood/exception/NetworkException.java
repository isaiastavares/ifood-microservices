package br.com.ifood.exception;

public class NetworkException extends Exception {
	
	private static final long serialVersionUID = -8228720376228006398L;

	public NetworkException(String message) {
		super(message);
	}
	
	public NetworkException(Exception exception) {
		super(exception);
	}

}
