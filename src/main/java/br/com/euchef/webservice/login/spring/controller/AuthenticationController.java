package br.com.euchef.webservice.login.spring.controller;

import br.com.euchef.webservice.login.spring.model.json.AuthenticationRequest;
import br.com.euchef.webservice.login.spring.model.json.AuthenticationResponse;
import br.com.euchef.webservice.login.spring.model.json.FacebookAuthenticationRequest;
import br.com.euchef.webservice.login.spring.service.AuthenticationService;
import br.com.euchef.webservice.login.spring.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${login.route.authentication.base}")
public class AuthenticationController {

    @Value("${login.token.header}")
    private String tokenHeader;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "${login.route.authentication.signIn}", method = RequestMethod.POST)
    public ResponseEntity<?> signIn(@RequestBody(required = true) @Validated AuthenticationRequest authenticationRequest) {
        String token = this.authenticationService.login(
                authenticationRequest.getEmail(),
                authenticationRequest.getType(),
                authenticationRequest.getPassword());

        // Return the token
        return ResponseEntity.ok().header(tokenHeader, token).body(new AuthenticationResponse(token));
    }

    @RequestMapping(value = "${login.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> authenticationRequest(HttpServletRequest request) {
        String refreshedToken = this.authenticationService.refresh(request.getHeader(this.tokenHeader));
        if (refreshedToken != null) {
            return ResponseEntity.ok(new AuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(value = "${login.route.authentication.signUp}", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody(required = true) @Validated AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(this.authenticationService.register(
                authenticationRequest.getEmail(),
                authenticationRequest.getType(),
                authenticationRequest.getPassword()));
    }

    @RequestMapping(value = "${login.route.authentication.currentUser}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> me() {
        return ResponseEntity.ok(securityService.getCurrentUser().get());
    }

    @RequestMapping(value = "${login.route.authentication.facebook.login}", method = RequestMethod.POST)
    public ResponseEntity<?> facebookLogin(@RequestBody(required = true) @Validated FacebookAuthenticationRequest facebookAuthenticationRequest) {
        String token = this.authenticationService.facebookLogin(facebookAuthenticationRequest.getAccessToken());
        // Return the token
        return ResponseEntity.ok().header(tokenHeader, token).body(new AuthenticationResponse(token));
    }
}
