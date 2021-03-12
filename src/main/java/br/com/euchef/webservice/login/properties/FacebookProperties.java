package br.com.euchef.webservice.login.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by idealize on 07/03/17.
 */
@Component
@ConfigurationProperties(prefix = "facebook")
public class FacebookProperties {

    private String login;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
