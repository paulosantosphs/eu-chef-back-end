package br.com.euchef.webservice.login.spring.model.json;

import javax.validation.constraints.NotNull;

/**
 * Created by idealize on 07/03/17.
 */
public class FacebookAuthenticationRequest {
    @NotNull
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
