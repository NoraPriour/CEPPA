package org.github.norapriour.ceppa_back;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Article {
    private Integer id;
    private String author;
    private String title;
    private String articleContent;

    public Article(Integer id, String author, String title, String articleContent) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.articleContent = articleContent;
    }

    @JsonProperty("title")
    public void setTitle(String title) { this.title = title; }

    @JsonProperty("author")
    public void setAuthor(String author) { this.author = author; }

    @JsonAlias({"article_content", "articleContent"})
    public void setArticleContent(String articleContent) { this.articleContent = articleContent; }

    public int getId() { return id; }
    public String getAuthor() { return author; }
    public String getTitle() { return title; }
    public String getArticleContent() { return articleContent; }
}