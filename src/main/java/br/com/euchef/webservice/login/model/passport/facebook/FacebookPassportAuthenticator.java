package br.com.euchef.webservice.login.model.passport.facebook;

import br.com.euchef.webservice.login.model.passport.PassportAuthenticator;
import br.com.euchef.webservice.login.model.user.User;
import br.com.euchef.webservice.login.repository.PassportRepository;
import br.com.euchef.webservice.login.repository.UserRepository;
import br.com.euchef.webservice.login.service.FacebookService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FacebookPassportAuthenticator extends PassportAuthenticator {

    @Autowired
    private FacebookService facebook;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PassportRepository passportRepo;

    @Override
    public Optional<User> authenticate(String email, String accessToken) {
        try {
            JSONObject obj = facebook.getUserFields(accessToken, "email");

            String realEmail = obj.getString("email");

            User user = userRepo.findByEmail(realEmail).orElseGet(() -> {
                User newUser = new User(realEmail);
                return userRepo.save(newUser);
            });
            FacebookPassport passport = (FacebookPassport) passportRepo
                    .findByUserIdAndType(user.getId(), FacebookPassport.class).orElseGet(() -> {
                        FacebookPassport newPassport = new FacebookPassport(user, accessToken);
                        return passportRepo.save(newPassport);
                    });
            passport.setAccessToken(accessToken);
            passportRepo.save(passport);
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.ofNullable(null);
        }

    }
}
