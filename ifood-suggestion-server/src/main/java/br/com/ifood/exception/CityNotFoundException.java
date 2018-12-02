package br.com.ifood.exception;

public class CityNotFoundException extends Exception {

	private static final long serialVersionUID = 7195138072520842587L;
	
	public CityNotFoundException(String message) {
		super(message);
	}
	
	public CityNotFoundException(Exception exception) {
		super(exception);
	}

}
