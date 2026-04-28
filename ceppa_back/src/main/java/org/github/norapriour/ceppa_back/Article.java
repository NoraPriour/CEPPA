package org.github.norapriour.ceppa_back;

public class Article {
    private String auteur;
    private String titre;
    private String texte;

    public Article(String auteur, String titre, String texte) {
        this.auteur = auteur;
        this.titre = titre;
        this.texte = texte;
    }

    public String getAuteur() { return auteur; }
    public String getTitre() { return titre; }
    public String getTexte() { return texte; }
    
}
