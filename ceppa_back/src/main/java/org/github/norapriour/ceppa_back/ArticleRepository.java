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
        return jdbc.query("SELECT a.id, a.title, a.article_content, COALESCE(u.user_name, 'Auteur inconnu') AS author FROM articles a LEFT JOIN users u ON a.author_id = u.id", (rs, rowNum) -> new Article(rs.getInt("id"), rs.getString("author"), rs.getString("title"), rs.getString("article_content")));
    }

    public void deleteById(int id) {
        jdbc.update("DELETE FROM articles WHERE id = ?", id);
    }


    public Article save(Article article) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
    Integer authorId = null;
    List<Integer> ids = jdbc.query(
        "SELECT id FROM users WHERE user_name = ?",
        (rs, rowNum) -> rs.getInt("id"),
        article.getAuthor()
    );
    if (!ids.isEmpty()) {
        authorId = ids.get(0);
    }

    final Integer finalAuthorId = authorId;
    jdbc.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO articles (title, article_content, author_id) VALUES (?, ?, ?)",
            new String[]{"id"}
        );
        ps.setString(1, article.getTitle());
        ps.setString(2, article.getArticleContent());
        if (finalAuthorId != null) {
            ps.setInt(3, finalAuthorId);
        } else {
            ps.setNull(3, java.sql.Types.INTEGER);
        }
        return ps;
    }, keyHolder);

    int id = keyHolder.getKey().intValue();
    return new Article(id, article.getAuthor(), article.getTitle(), article.getArticleContent());
}
}
