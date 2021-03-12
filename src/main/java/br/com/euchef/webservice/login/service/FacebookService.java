package br.com.euchef.webservice.login.service;

import br.com.euchef.webservice.login.model.passport.facebook.FacebookAccessException;
import br.com.euchef.webservice.login.model.passport.facebook.FacebookConfiguration;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Component
public class FacebookService {

	@Autowired
	private FacebookConfiguration config;

	public String getLoginRedirectURL() {
		return config.getLoginRedirectURL();
	}

	public String getAccessToken(String authCode) {
		try {
			String retorno = readURL(new URL(config.getAuthURL(authCode)));
			String[] pairs = retorno.split("&");
			for (String pair : pairs) {
				String[] kv = pair.split("=");
				if (kv.length != 2) {
					throw new FacebookAccessException("Resposta auth inesperada.");
				} else if (kv[0].equals("access_token")) {
					return kv[1];
				}
			}
			throw new FacebookAccessException("Access Token nao retornado");
		} catch (IOException e) {
			throw new FacebookAccessException(e.getMessage());
		}
	}

	public JSONObject getUserFields(String accessToken, String... fields) {
		try {
			JSONObject obj = new JSONObject(readURL(new URL(config.getMeUrl(accessToken, fields))));
			obj.put("accessToken", accessToken);
			return obj;
		} catch (JSONException | IOException e) {
			throw new FacebookAccessException(e.getMessage());
		}
	}

	private String readURL(URL url) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = url.openStream();
		int r;
		while ((r = is.read()) != -1) {
			baos.write(r);
		}
		return new String(baos.toByteArray());
	}

	public JSONObject getUserFieldsByAuthCode(String authCode, String... fields) {

		String accessToken = getAccessToken(authCode);
		JSONObject obj = getUserFields(accessToken, fields);
		obj.put("accessToken", accessToken);
		return obj;

	}

}
