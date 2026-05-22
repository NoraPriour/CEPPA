package org.github.norapriour.ceppa_back;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<User> findAll() {
        return jdbc.query(
                "SELECT id, keycloak_id, user_name, email FROM users ORDER BY id",
                (rs, rowNum) -> new User(
                        rs.getLong("id"),
                        rs.getString("keycloak_id"),
                        rs.getString("user_name"),
                        rs.getString("email")
                )
        );
    }

    public void deleteById(Long id) {
        jdbc.update("DELETE FROM users WHERE id = ?", id);
    }

    public Optional<User> findById(Long id) {
        List<User> users = jdbc.query(
                "SELECT id, keycloak_id, user_name, email FROM users WHERE id = ?",
                (rs, rowNum) -> new User(
                        rs.getLong("id"),
                        rs.getString("keycloak_id"),
                        rs.getString("user_name"),
                        rs.getString("email")
                ),
                id
        );

        return users.stream().findFirst();
    }

    public User save(CreateUserRequest request, String keycloakId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users (keycloak_id, user_name, email) VALUES (?, ?, ?)", new String[]{"id"});
            ps.setString(1, keycloakId);
            ps.setString(2, request.getUserName());
            ps.setString(3, request.getEmail());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new User(id, keycloakId, request.getUserName(), request.getEmail());
    }

    public User linkKeycloakId(Long id, String keycloakId) {
        jdbc.update("UPDATE users SET keycloak_id = ? WHERE id = ?", keycloakId, id);

        return jdbc.queryForObject(
                "SELECT id, keycloak_id, user_name, email FROM users WHERE id = ?",
                (rs, rowNum) -> new User(
                        rs.getLong("id"),
                        rs.getString("keycloak_id"),
                        rs.getString("user_name"),
                        rs.getString("email")
                ),
                id
        );
    }
}
