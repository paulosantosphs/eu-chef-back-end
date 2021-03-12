package br.com.euchef.webservice.login.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "route")
public class RouteProperties {

    private RouteAuthProperties authentication;


    public RouteAuthProperties getAuthentication() {
        return authentication;
    }

    public void setAuthentication(RouteAuthProperties authentication) {
        this.authentication = authentication;
    }
}
