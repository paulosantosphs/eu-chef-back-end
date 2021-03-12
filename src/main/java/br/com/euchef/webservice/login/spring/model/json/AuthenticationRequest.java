package br.com.euchef.webservice.login.spring.model.json;

import br.com.euchef.webservice.login.model.passport.PassportType;

import javax.validation.constraints.NotNull;

public class AuthenticationRequest {
	@NotNull
	private String email;

	@NotNull
	private String password;

	@NotNull
	private PassportType type;

	public AuthenticationRequest() {
		super();
	}

	public AuthenticationRequest(String email, String password, PassportType type) {
		this.setEmail(email);
		this.setPassword(password);
		this.setType(type);
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public PassportType getType() {
		return type;
	}

	public void setType(PassportType type) {
		this.type = type;
	}
}
