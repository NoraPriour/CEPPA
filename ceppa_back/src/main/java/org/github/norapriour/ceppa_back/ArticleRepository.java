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

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO articles (titre, texte, auteur_id) VALUES (?, ?, ?)", new String[]{"id"});
            ps.setString(1, article.getTitre());
            ps.setString(2, article.getTexte());
            ps.setString(3, article.getAuteur());
            return ps;
        }, keyHolder);

        int id = keyHolder.getKey().intValue();
        return new Article(id, article.getAuteur(), article.getTitre(), article.getTexte());
    }
}
