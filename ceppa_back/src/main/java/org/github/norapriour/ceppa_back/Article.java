package org.github.norapriour.ceppa_back;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Article {
    private Long id;
    private String auteur;
    private String titre;
    private String texte;

    public Article(Long id, String auteur, String titre, String texte) {
        this.id = id;
        this.auteur = auteur;
        this.titre = titre;
        this.texte = texte;
    }

    public Long getId() {
        return id;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getTitre() {
        return titre;
    }

    public String getTexte() {
        return texte;
    }

    @JsonProperty("author")
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    @JsonProperty("title")
    public void setTitre(String titre) {
        this.titre = titre;
    }

    @JsonProperty("text")
    public void setTexte(String texte) {
        this.texte = texte;
    }
}
