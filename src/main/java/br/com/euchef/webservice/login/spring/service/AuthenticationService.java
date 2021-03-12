package br.com.euchef.webservice.login.spring.service;

import br.com.euchef.webservice.login.model.passport.PassportType;
import br.com.euchef.webservice.login.model.user.User;

public interface AuthenticationService {

    public String login(String username, PassportType type, String password);

    public String refresh(String token);

    public User register(String username, PassportType type, String password);

    public String facebookLogin(String authCode);
}
