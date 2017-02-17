package org.shanoir.ng.controller.rest;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * Abstract class used to manage Keycloak token.
 * 
 * @author msimon
 *
 */
public abstract class KeycloakControllerTestIT {

	@Value("${keycloak.auth-server-url}")
	private String keycloakAuthServerUrl;

	@Value("${keycloak.realm}")
	private String keycloakRealm;

	@Value("${keycloak.resource}")
	private String keycloakResource;

	@Value("${keycloak.credentials.secret}")
	private String keycloakCredentialsSecret;

	/*
	 * Obtain headers for Keycloack authentication.
	 * 
	 * @return headers.
	 */
	protected HttpHeaders getHeadersWithToken() {
		final AccessTokenResponse tokenResponse = getToken();
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + tokenResponse.getToken());
		return headers;
	}

	/*
	 * Obtain a token on behalf of ngular2-product. Send credentials through
	 * direct access api:
	 * http://docs.jboss.org/keycloak/docs/1.2.0.CR1/userguide/html/direct-
	 * access-grants.html Make sure the realm has the Direct Grant API switch ON
	 * (it can be found on Settings/Login page!)
	 * 
	 * @return response with Keycloak token.
	 */
	private AccessTokenResponse getToken() {
		Keycloak keycloak = Keycloak.getInstance(keycloakAuthServerUrl, keycloakRealm, "admin", "admin",
				keycloakResource, keycloakCredentialsSecret);
		return keycloak.tokenManager().getAccessToken();
	}

}
