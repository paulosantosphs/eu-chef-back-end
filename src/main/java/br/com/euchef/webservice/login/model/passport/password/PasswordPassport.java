package br.com.euchef.webservice.login.model.passport.password;

import br.com.euchef.webservice.login.model.passport.Passport;
import br.com.euchef.webservice.login.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(PasswordPassport.DESCRIMINATOR)
public class PasswordPassport extends Passport {
	public static final String DESCRIMINATOR = "PasswordPassport";

	@JsonIgnore
	private String encryptedPassword;

	// constructor
	public PasswordPassport(User user) {
		super(user);
	}

	protected PasswordPassport() {
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setPassword(String password) {
		this.encryptedPassword = new BCryptPasswordEncoder().encode(password);
	}

}
