package br.com.euchef.webservice.login.spring.service;

import br.com.euchef.webservice.login.model.user.User;

import java.util.Optional;

public interface SecurityService {

	public Optional<User> getCurrentUser();

}
