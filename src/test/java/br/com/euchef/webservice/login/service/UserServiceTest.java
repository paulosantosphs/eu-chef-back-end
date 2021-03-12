package br.com.euchef.webservice.login.service;

import br.com.euchef.webservice.login.model.passport.Passport;
import br.com.euchef.webservice.login.model.passport.PassportType;
import br.com.euchef.webservice.login.model.passport.facebook.FacebookPassport;
import br.com.euchef.webservice.login.model.passport.password.PasswordPassport;
import br.com.euchef.webservice.login.model.passport.password.PasswordPassportAuthenticator;
import br.com.euchef.webservice.login.model.user.User;
import br.com.euchef.webservice.login.repository.PassportRepository;
import br.com.euchef.webservice.login.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;

public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @InjectMocks
    private PasswordPassportAuthenticator passwordPassportAuthenticator;

    @Mock
    private PassportRepository passportRepo;

    @Mock
    private UserRepository userRepo;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    // dado um usuário e um tipo de passaport, retorne o passaporte do tipo
    // correto
    @Test
    public void testGetPasswordPassport() {
        User user = new User();
        user.setId(23l);

        PasswordPassport manualCreatedPassport = new PasswordPassport(user);
        manualCreatedPassport.setId(250l);

        Mockito.when(passportRepo.findByUserIdAndType(23l, PasswordPassport.class))
                .thenReturn(Optional.of(manualCreatedPassport));

        Passport passport = service.getPassport(user, PassportType.PASSWORD).get();

        assertNotNull("Passaporte nao existe", passport);

        assertTrue("Passport deve ser uma instacia de PasswordPassport", passport instanceof PasswordPassport);

        assertEquals("Passaporte deve ser o esperado", passport.getId(), manualCreatedPassport.getId());

        assertEquals("Passaporte deve ser do usuario indicado", user.getId(), passport.getUser().getId());
    }

    @Test
    public void testFindUserbyEmail() {
        User manualCreatedUser = new User();
        manualCreatedUser.setId(25l);
        manualCreatedUser.setEmail("gandra.vinicius@gmail.com");

        Mockito.when(userRepo.findByEmail("gandra.vinicius@gmail.com"))
                .thenReturn(Optional.of(manualCreatedUser));

        User returnedUser = service.findUserByEmail("gandra.vinicius@gmail.com").get();

        assertEquals("Usuário deve ter o email esperado", returnedUser.getEmail(), manualCreatedUser.getEmail());

        assertEquals("Usuário deve ter o mesmo Id", returnedUser.getId(), manualCreatedUser.getId());

    }

    @Test
    public void testRegisterUserPasswordPass() {
        User manualCreatedUser = new User();
        manualCreatedUser.setEmail("gandra.vinicius@gmail.com");

        PasswordPassport manualCreatedPassport = new PasswordPassport(manualCreatedUser);
        manualCreatedPassport.setPassword("vini1234");

        Mockito.when(userRepo.findByEmail("gandra.vinicius@gmail.com"))
                .thenReturn(Optional.of(manualCreatedUser));

        Mockito.when(passportRepo.findByUserIdAndType(manualCreatedUser.getId(), PassportType.PASSWORD.getType()))
                .thenReturn(Optional.of(manualCreatedPassport));

        assertEquals("Autenticador falhou",
                service.register("gandra.vinicius@gmail.com", "vini1234")
                        .getEmail(),
                manualCreatedUser.getEmail());
    }

    @Test
    public void testUserAuthenticator() {
        User manualCreatedUser = new User();
        manualCreatedUser.setEmail("gandra.vinicius@gmail.com");

        PasswordPassport manualCreatedPassport = new PasswordPassport(manualCreatedUser);
        manualCreatedPassport.setPassword("vini1234");

        Mockito.when(userRepo.findByEmail("gandra.vinicius@gmail.com"))
                .thenReturn(Optional.of(manualCreatedUser));

        Mockito.when(passportRepo.findByUserIdAndType(manualCreatedUser.getId(), PassportType.PASSWORD.getType()))
                .thenReturn(Optional.of(manualCreatedPassport));

        assertEquals("Autenticador falhou",
                passwordPassportAuthenticator.authenticate("gandra.vinicius@gmail.com", "vini1234").get()
                        .getEmail(),
                manualCreatedUser.getEmail());
    }

    @Test
    public void testGetFacebookPassport() {
        User user = new User();
        user.setId(23l);

        FacebookPassport manualCreatedPassport = new FacebookPassport(user);
        manualCreatedPassport.setId(250l);

        Mockito.when(passportRepo.findByUserIdAndType(23l, FacebookPassport.class))
                .thenReturn(Optional.of(manualCreatedPassport));

        Passport passport = service.getPassport(user, PassportType.FACEBOOK).get();

        assertNotNull("Passaporte nao existe", passport);

        assertTrue("Passport deve ser uma instacia de PasswordPassport", passport instanceof FacebookPassport);

        assertEquals("Passaporte deve ser o esperado", passport.getId(), manualCreatedPassport.getId());

        assertEquals("Passaporte deve ser do usuario indicado", user.getId(), passport.getUser().getId());

    }

}
