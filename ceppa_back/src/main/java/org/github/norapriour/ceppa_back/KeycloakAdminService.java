package org.github.norapriour.ceppa_back;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
public class KeycloakAdminService {

    private final RestClient restClient;
    private final String serverUrl;
    private final String realm;
    private final String clientId;
    private final String clientSecret;

    public KeycloakAdminService(
            @Value("${keycloak.admin.server-url}") String serverUrl,
            @Value("${keycloak.admin.realm}") String realm,
            @Value("${keycloak.admin.client-id}") String clientId,
            @Value("${keycloak.admin.client-secret}") String clientSecret
    ) {
        this.restClient = RestClient.create();
        this.serverUrl = serverUrl;
        this.realm = realm;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String createUser(CreateUserRequest request) {
        String token = getAdminToken();

        Map<String, Object> body = Map.of(
                "username", request.getUserName(),
                "email", request.getEmail(),
                "enabled", true,
                "emailVerified", true,
                "requiredActions", List.of("UPDATE_PASSWORD"),
                "credentials", List.of(Map.of(
                        "type", "password",
                        "value", request.getTemporaryPassword(),
                        "temporary", true
                ))
        );

        URI location = restClient.post()
                .uri("%s/admin/realms/%s/users".formatted(serverUrl, realm))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .toBodilessEntity()
                .getHeaders()
                .getLocation();

        if (location == null) {
            throw new IllegalStateException("Keycloak did not return the created user location.");
        }

        String path = location.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }

    public void deleteUser(String keycloakId) {
        if (keycloakId == null || keycloakId.isBlank()) {
            return;
        }

        restClient.delete()
                .uri("%s/admin/realms/%s/users/%s".formatted(serverUrl, realm, keycloakId))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAdminToken())
                .retrieve()
                .toBodilessEntity();
    }

    private String getAdminToken() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);

        KeycloakTokenResponse response = restClient.post()
                .uri("%s/realms/%s/protocol/openid-connect/token".formatted(serverUrl, realm))
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .body(form)
                .retrieve()
                .body(KeycloakTokenResponse.class);

        if (response == null || response.accessToken() == null) {
            throw new IllegalStateException("Keycloak did not return an admin access token.");
        }

        return response.accessToken();
    }

    private record KeycloakTokenResponse(
            @com.fasterxml.jackson.annotation.JsonProperty("access_token") String accessToken
    ) {
    }
}
