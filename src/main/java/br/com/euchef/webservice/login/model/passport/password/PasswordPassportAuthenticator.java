package br.com.euchef.webservice.login.model.passport.password;

import br.com.euchef.webservice.login.model.passport.PassportAuthenticator;
import br.com.euchef.webservice.login.model.user.User;
import br.com.euchef.webservice.login.repository.PassportRepository;
import br.com.euchef.webservice.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class PasswordPassportAuthenticator extends PassportAuthenticator {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PassportRepository passportRepo;

	@Override
	public Optional<User> authenticate(String email, String password) {
		User user = userRepo.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Usuário não registrado"));
		PasswordPassport passport = (PasswordPassport) passportRepo.findByUserIdAndType(user.getId(), PasswordPassport.class).get();
		BCryptPasswordEncoder encrypted = new BCryptPasswordEncoder();
		return Optional.ofNullable(encrypted.matches(password, passport.getEncryptedPassword()) ? user : null);
	}

}
