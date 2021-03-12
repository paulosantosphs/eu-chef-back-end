package br.com.euchef.webservice.login.model.passport;

import br.com.euchef.webservice.login.model.user.User;

import java.util.Optional;

public abstract class PassportAuthenticator {

	abstract public Optional<User> authenticate(String email, String authCode);

}
