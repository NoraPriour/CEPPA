package org.github.norapriour.ceppa_back;

public class User {
    private Long id;
    private String userName;
    private String email = "email@default.com";
    private String password = "0000";

    public User(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public Long getId() { return id; }
    public String getUserName() { return userName; }
    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return password;
    }
}