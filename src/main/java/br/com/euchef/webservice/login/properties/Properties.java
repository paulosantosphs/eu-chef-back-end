package br.com.euchef.webservice.login.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
@ConfigurationProperties(prefix = "login")
public class Properties {
    @Valid
    private RouteProperties route;

    @Valid
    private TokenProperties token;

    public RouteProperties getRoute() {
        return route;
    }

    public void setRoute(RouteProperties route) {
        this.route = route;
    }

    public TokenProperties getToken() {
        return token;
    }

    public void setToken(TokenProperties token) {
        this.token = token;
    }
}
