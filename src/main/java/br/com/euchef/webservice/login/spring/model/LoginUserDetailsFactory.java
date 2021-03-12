package br.com.euchef.webservice.login.spring.model;

import br.com.euchef.webservice.login.model.passport.PassportType;
import br.com.euchef.webservice.login.model.passport.password.PasswordPassport;
import br.com.euchef.webservice.login.model.user.User;
import br.com.euchef.webservice.login.repository.PassportRepository;
import br.com.euchef.webservice.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Component
public class LoginUserDetailsFactory {

	@Autowired
	private PassportRepository passRepo;

	@Autowired
	private UserRepository userRepo;

	public LoginUserDetails create(User user) {

		PasswordPassport password = (PasswordPassport) passRepo.findByUserIdAndType(user.getId(),
				PassportType.PASSWORD.getType()).orElse(new PasswordPassport(user));

		return new LoginUserDetails(user.getEmail(), password.getEncryptedPassword(),
				AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRolesCommaSepareted()));
	}

	public LoginUserDetails create(String username) {
		return create(userRepo.findByEmail(username).orElse(null));
	}

}
