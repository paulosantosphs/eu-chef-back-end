package br.com.euchef.webservice.login.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by idealize on 17/02/17.
 */
@Component
@ConfigurationProperties(prefix = "authentication")
public class RouteAuthProperties {

    private String base;

    private String singIn;

    private String singUp;

    private String refresh;

    private String currentUser;

    private FacebookProperties facebookProperties;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getSingIn() {
        return singIn;
    }

    public void setSingIn(String singIn) {
        this.singIn = singIn;
    }

    public String getSingUp() {
        return singUp;
    }

    public void setSingUp(String singUp) {
        this.singUp = singUp;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public FacebookProperties getFacebookProperties() {
        return facebookProperties;
    }

    public void setFacebookProperties(FacebookProperties facebookProperties) {
        this.facebookProperties = facebookProperties;
    }
}
