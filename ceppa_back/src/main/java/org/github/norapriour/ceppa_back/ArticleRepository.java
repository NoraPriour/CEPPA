package org.github.norapriour.ceppa_back;

import java.sql.PreparedStatement;
import java.util.List;

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
        return jdbc.query("SELECT a.id, a.titre, a.texte, COALESCE(u.user_name, 'Auteur inconnu') AS auteur FROM articles a LEFT JOIN users u ON a.auteur_id = u.id", (rs, rowNum) -> new Article(rs.getInt("id"), rs.getString("auteur"), rs.getString("titre"), rs.getString("texte")));
    }

    public Article save(Article article) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
    Integer auteurId = null;
    List<Integer> ids = jdbc.query(
        "SELECT id FROM users WHERE user_name = ?",
        (rs, rowNum) -> rs.getInt("id"),
        article.getAuteur()
    );
    if (!ids.isEmpty()) {
        auteurId = ids.get(0);
    }

    // 2. Insert avec auteurId (int ou null)
    final Integer finalAuteurId = auteurId; // nécessaire pour la lambda
    jdbc.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO articles (titre, texte, auteur_id) VALUES (?, ?, ?)",
            new String[]{"id"}
        );
        ps.setString(1, article.getTitre());
        ps.setString(2, article.getTexte());
        if (finalAuteurId != null) {
            ps.setInt(3, finalAuteurId);
        } else {
            ps.setNull(3, java.sql.Types.INTEGER);
        }
        return ps;
    }, keyHolder);

    int id = keyHolder.getKey().intValue();
    return new Article(id, article.getAuteur(), article.getTitre(), article.getTexte());
}
}
