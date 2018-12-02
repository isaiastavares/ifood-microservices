package br.com.ifood.exception;

public class BusinessException extends Exception {

	private static final long serialVersionUID = -3533876739349293117L;
	
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(Exception exception) {
		super(exception);
	}

}
