package br.com.euchef.webservice.login.spring.config;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			org.springframework.security.core.AuthenticationException arg2) throws IOException, ServletException {
		httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
	}
}
