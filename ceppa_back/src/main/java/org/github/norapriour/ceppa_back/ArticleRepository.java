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
        return jdbc.query("SELECT a.id, a.titre, a.texte, COALESCE(u.user_name, 'Auteur inconnu') AS auteur FROM articles a LEFT JOIN users u ON a.auteur_id = u.id", (rs, rowNum) -> new Article(rs.getInt("id"), rs.getString("auteur"), rs.getString("titre"), rs.getString("texte")));
    }
}
