package org.github.norapriour.ceppa_back;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private Long id;
    private String keycloakId;
    private String userName;
    private String email;

    @JsonCreator
    public User(
            @JsonProperty("id") Long id,
            @JsonProperty("keycloakId") String keycloakId,
            @JsonProperty("userName") String userName,
            @JsonProperty("email") String email
    ) {
        this.id = id;
        this.keycloakId = keycloakId;
        this.userName = userName;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getKeycloakId() { return keycloakId; }
    public String getUserName() { return userName; }
    public String getEmail() { return email; }
}
