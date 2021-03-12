package br.com.euchef.webservice.login.service;

import java.util.Optional;

import br.com.euchef.webservice.login.model.passport.Passport;
import br.com.euchef.webservice.login.model.passport.PassportAuthenticator;
import br.com.euchef.webservice.login.model.passport.PassportType;
import br.com.euchef.webservice.login.model.passport.password.PasswordPassport;
import br.com.euchef.webservice.login.model.user.User;
import br.com.euchef.webservice.login.repository.PassportRepository;
import br.com.euchef.webservice.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class UserService {

	@Autowired
	private PassportRepository passportRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AutowireCapableBeanFactory beanFactory;

	/**
	 * Dado um usuário e tipo de passaporte método retorna o passaporte
	 * 
	 * @param user
	 * @param type
	 * @return passaporte do tipo requisitado
	 */
	public Optional<Passport> getPassport(User user, PassportType type) {
		return passportRepo.findByUserIdAndType(user.getId(), type.getType());
	}

	/**
	 * Dado um email, função retorna usuário que possui o email dado
	 * 
	 * @param email
	 * @return usuario com dado email se existir e null caso contrario
	 */
	public Optional<User> findUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	/**
	 * Dado um usuario o tipo do passaporte e senha, método retorna se ele é
	 * valido e se a senha está correta
	 * 
	 * @param user
	 * @param type
	 * @param senha
	 * @return retorna usuario autenticado
	 */
	public Optional<User> authenticateUser(String email, PassportType type, String authString) {

		if (type == null) {
			throw new RuntimeException("Tipo de autenticação inválido.");
		}

		try {
			// injeta o autenticador manualmente
			PassportAuthenticator authenticator = type.getAuthenticatorType().newInstance();
			beanFactory.autowireBean(authenticator);
			return authenticator.authenticate(email, authString);
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
	}

	/**
	 * Registra usuario no banco dado email e senha ou retorna usuario existente
	 * com o mesmo email mas não altera senha. Caso usuario ja exista mas nao
	 * tenha passaporte de senha, adiciona passaporte de senha e retorna usuario
	 * 
	 * @param email
	 * @param password
	 * @return retorna usuario registrado
	 */
	public User register(String email, String password) {

		if (!userRepo.findByEmail(email).isPresent()) {

			User user = new User(email);
			PasswordPassport wordPass = new PasswordPassport(user);
			wordPass.setPassword(password);

			userRepo.save(user);
			passportRepo.save(wordPass);

			return user;
		}

		User user = userRepo.findByEmail(email).get();
		if (!passportRepo.findByUserIdAndType(user.getId(),
				PassportType.PASSWORD.getType()).isPresent()) {
			PasswordPassport wordPass = new PasswordPassport(user);
			wordPass.setPassword(password);
			passportRepo.save(wordPass);
		}

		return user;
	}
}
