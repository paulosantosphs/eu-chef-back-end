package br.com.euchef.webservice.login.spring.service.impl;

import br.com.euchef.webservice.login.model.passport.PassportType;
import br.com.euchef.webservice.login.model.passport.facebook.FacebookPassportAuthenticator;
import br.com.euchef.webservice.login.model.user.User;
import br.com.euchef.webservice.login.service.FacebookService;
import br.com.euchef.webservice.login.service.UserService;
import br.com.euchef.webservice.login.spring.config.TokenUtils;
import br.com.euchef.webservice.login.spring.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private FacebookService facebookService;

    @Autowired
    private FacebookPassportAuthenticator facebookPassportAuthenticator;

    public String login(String username, PassportType type, String authString) {

        userService.authenticateUser(username, type, authString).orElseThrow(() -> new BadCredentialsException("Usuário e/ou senha inválidos"));

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        // Perform the authentication
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities()));

        // Reload password post-authentication so we can generate token
        return this.tokenUtils.generateToken(userDetails);
    }

    public String refresh(String token) {
        if (this.tokenUtils.canTokenBeRefreshed(token)) {
            return this.tokenUtils.refreshToken(token);
        } else {
            return null;
        }
    }

    @Override
    public User register(String username, PassportType type, String password) {
        if (PassportType.PASSWORD.equals(type)) {
            return userService.register(username, password);
        } else {
            return userService.authenticateUser(username, type, password).orElseThrow(() -> new BadCredentialsException("Algum dado inválido foi informado"));
        }
    }


    public String facebookLogin(String accessToken) {
        User user = facebookPassportAuthenticator.authenticate("", accessToken).get();
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getEmail());
        return this.tokenUtils.generateToken(userDetails);
    }

}
