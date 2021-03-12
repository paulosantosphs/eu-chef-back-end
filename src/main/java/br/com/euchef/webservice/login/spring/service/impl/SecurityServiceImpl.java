package br.com.euchef.webservice.login.spring.service.impl;

import java.util.Optional;

import br.com.euchef.webservice.login.model.user.User;
import br.com.euchef.webservice.login.repository.UserRepository;
import br.com.euchef.webservice.login.spring.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityServiceImpl implements SecurityService {
	@Autowired
	private UserRepository userRepo;

	public Optional<User> getCurrentUser() {
		Authentication details = SecurityContextHolder.getContext().getAuthentication();

		if (details == null) {
			return Optional.ofNullable(null);
		}

		return userRepo.findByEmail(details.getName());
	}

}
