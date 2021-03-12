package br.com.euchef.webservice.login.spring.service.impl;

import br.com.euchef.webservice.login.spring.model.LoginUserDetailsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private LoginUserDetailsFactory userFac;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			return userFac.create(username);
		} catch (Exception e) {
			throw new UsernameNotFoundException("Username not found", e);
		}
	}

}
