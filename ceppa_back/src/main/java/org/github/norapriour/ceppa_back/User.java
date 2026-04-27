package org.github.norapriour.ceppa_back;

public class User {
    private Long id;
    private String userName;

    public User(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public Long getId() { return id; }
    public String getUserName() { return userName; }
}