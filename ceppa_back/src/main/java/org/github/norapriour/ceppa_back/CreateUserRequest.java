package org.github.norapriour.ceppa_back;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserRequest {
    private final String userName;
    private final String email;
    private final String temporaryPassword;

    @JsonCreator
    public CreateUserRequest(
            @JsonProperty("userName") String userName,
            @JsonProperty("email") String email,
            @JsonProperty("temporaryPassword") String temporaryPassword
    ) {
        this.userName = userName;
        this.email = email;
        this.temporaryPassword = temporaryPassword;
    }

    public String getUserName() { return userName; }
    public String getEmail() { return email; }
    public String getTemporaryPassword() { return temporaryPassword; }
}
