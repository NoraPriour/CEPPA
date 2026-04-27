package org.github.norapriour.ceppa_back;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<User> findAll() {
        return jdbc.query(
                "SELECT id, user_name FROM users",
                (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("user_name"))
        );
    }

    public void deleteById(Long id) {
        jdbc.update("DELETE FROM users WHERE id = ?", id);
    }
}