package br.com.euchef.webservice.login.model.passport.facebook;

public class FacebookAccessException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FacebookAccessException() {
		super();
	}

	public FacebookAccessException(String message) {
		super(message);
	}
}
