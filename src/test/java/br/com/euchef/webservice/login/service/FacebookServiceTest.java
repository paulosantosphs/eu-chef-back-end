package br.com.euchef.webservice.login.service;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import br.com.euchef.webservice.login.model.passport.PassportType;
import br.com.euchef.webservice.login.model.passport.facebook.FacebookPassport;
import br.com.euchef.webservice.login.model.passport.facebook.FacebookPassportAuthenticator;
import br.com.euchef.webservice.login.model.user.User;
import br.com.euchef.webservice.login.repository.PassportRepository;
import br.com.euchef.webservice.login.repository.UserRepository;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class FacebookServiceTest {

	@InjectMocks
	private FacebookPassportAuthenticator facebookPassportAuthenticator;

	@Mock
	private PassportRepository passportRepo;

	@Mock
	private UserRepository userRepo;

	@Mock
	private FacebookService facebook;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFacebookAuthenticatorByAccessToken() {

		String accessToken = "EAAXfIY1WbqQBALMFyqKrSLTfmrkV1rkRrIdQsHDPxSLvuZCv25qd8hx4aRDnWX7sQRarMbu8fJ4YxH0zYXylQrufKSSxWoZApKBMHZCrs5J057CUlIKUdED0SzZBG4lA8mZBUWGUZBjqUXGnnmp2KldfrwTQoWInNz1Ljg4PZBtKgZDZD";

		User manualCreatedUser = new User();
		manualCreatedUser.setEmail("gandra.vinicius@gmail.com");

		JSONObject obj = new JSONObject();
		obj.put("email", "gandra.vinicius@gmail.com");
		obj.put("accessToken", accessToken);

		FacebookPassport manualCreatedPassport = new FacebookPassport(manualCreatedUser);
		manualCreatedPassport.setAccessToken(accessToken);

		Mockito.when(userRepo.findByEmail("gandra.vinicius@gmail.com"))
				.thenReturn(Optional.of(manualCreatedUser));

		Mockito.when(passportRepo.findByUserIdAndType(manualCreatedUser.getId(),
				PassportType.FACEBOOK.getType()))
				.thenReturn(Optional.of(manualCreatedPassport));

		Mockito.when(facebook.getUserFields(accessToken, "email"))
				.thenReturn(obj);

		assertEquals("Autenticador falhou",
				facebookPassportAuthenticator.authenticate("gandra.vinicius@gmail.com",
						accessToken).get()
						.getEmail(),
				manualCreatedUser.getEmail());

		// Certifica-se que o repositorio salva o passaporte no final da função
		Mockito.verify(passportRepo,
				Mockito.times(1)).save(manualCreatedPassport);

	}
}
