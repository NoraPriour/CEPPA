package org.github.norapriour.ceppa_back;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepository {

    private final JdbcTemplate jdbc;

    public ArticleRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Article> findAll() {
        return jdbc.query(
                """
                        SELECT a.id, a.titre, a.texte, COALESCE(u.user_name, 'Auteur inconnu') AS auteur
                        FROM articles a
                        LEFT JOIN users u ON a.auteur_id = u.id
                        ORDER BY a.id
                        """,
                (rs, rowNum) -> new Article(
                        rs.getLong("id"),
                        rs.getString("auteur"),
                        rs.getString("titre"),
                        rs.getString("texte"))
        );
    }

    public Optional<Article> findById(Long id) {
        List<Article> articles = jdbc.query(
                """
                        SELECT a.id, a.titre, a.texte, COALESCE(u.user_name, 'Auteur inconnu') AS auteur
                        FROM articles a
                        LEFT JOIN users u ON a.auteur_id = u.id
                        WHERE a.id = ?
                        """,
                (rs, rowNum) -> new Article(
                        rs.getLong("id"),
                        rs.getString("auteur"),
                        rs.getString("titre"),
                        rs.getString("texte")),
                id
        );

        return articles.stream().findFirst();
    }

    public void deleteById(Long id) {
        jdbc.update("DELETE FROM articles WHERE id = ?", id);
    }

    public Article save(Article article) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Long auteurId = findAuteurId(article.getAuteur()).orElse(null);

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO articles (titre, texte, auteur_id) VALUES (?, ?, ?)",
                    new String[]{"id"}
            );
            ps.setString(1, article.getTitre());
            ps.setString(2, article.getTexte());
            if (auteurId != null) {
                ps.setLong(3, auteurId);
            } else {
                ps.setNull(3, Types.BIGINT);
            }
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new Article(id, article.getAuteur(), article.getTitre(), article.getTexte());
    }

    private Optional<Long> findAuteurId(String auteur) {
        List<Long> ids = jdbc.query(
                "SELECT id FROM users WHERE user_name = ?",
                (rs, rowNum) -> rs.getLong("id"),
                auteur
        );

        return ids.stream().findFirst();
    }
}
