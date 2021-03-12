package br.com.euchef.webservice.login.model.passport.facebook;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "login.facebook")
public class FacebookConfiguration {

	private String appId;
	private String appSecret;
	private String redirectUri;
	private String display = "page";
	private String scope = "email";
	private String facebookAuthorizeUrl = "https://graph.facebook.com/oauth/authorize";
	private String facebookAccessUrl = "https://graph.facebook.com/oauth/access_token";
	private String facebookMeUrl = "https://graph.facebook.com/me";

	public String getLoginRedirectURL() {
		return facebookAuthorizeUrl
				+ "?client_id=" + appId
				+ "&display=" + display
				+ "&redirect_uri=" + redirectUri
				+ "&scope=" + scope;
	}

	public String getAuthURL(String authCode) {
		return facebookAccessUrl
				+ "?client_id=" + appId
				+ "&redirect_uri=" + redirectUri
				+ "&client_secret=" + appSecret
				+ "&code=" + authCode;
	}

	public String getMeUrl(String accessToken, String... fields) {
		return facebookMeUrl
				+ "?access_token=" + accessToken
				+ "&fields=" + joinFields(fields);
	}

	private String joinFields(String... fields) {
		String fieldsConcat = "";
		for (String field : fields) {
			fieldsConcat += field + ",";
		}
		return fieldsConcat.substring(0, fieldsConcat.length() - 1);
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getFacebookAuthorizeUrl() {
		return facebookAuthorizeUrl;
	}

	public void setFacebookAuthorizeUrl(String facebookAuthorizeUrl) {
		this.facebookAuthorizeUrl = facebookAuthorizeUrl;
	}

	public String getFacebookAccessUrl() {
		return facebookAccessUrl;
	}

	public void setFacebookAccessUrl(String facebookAccessUrl) {
		this.facebookAccessUrl = facebookAccessUrl;
	}

	public String getFacebookMeUrl() {
		return facebookMeUrl;
	}

	public void setFacebookMeUrl(String facebookMeUrl) {
		this.facebookMeUrl = facebookMeUrl;
	}
}
