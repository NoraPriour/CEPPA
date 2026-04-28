package org.github.norapriour.ceppa_back;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepository {
    
    private final JdbcTemplate jdbc;

    public ArticleRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Article> findAll() {
        return jdbc.query("SELECT auteur, titre, texte FROM articles", (rs, rowNum) -> new Article(rs.getString("auteur"), rs.getString("titre"), rs.getString("texte")));
    }

}
