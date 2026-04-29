package org.github.norapriour.ceppa_back;

public class Article {
    private int id;
    private String auteur;
    private String titre;
    private String texte;

    public Article(int id, String auteur, String titre, String texte) {
        this.id = id;
        this.auteur = auteur;
        this.titre = titre;
        this.texte = texte;
    }

    public int getId() { return id; }
    public String getAuteur() { return auteur; }
    public String getTitre() { return titre; }
    public String getTexte() { return texte; }
}