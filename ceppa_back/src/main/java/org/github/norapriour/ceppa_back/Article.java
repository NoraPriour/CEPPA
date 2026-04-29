package org.github.norapriour.ceppa_back;

public class Article {
    private int auteur_id;
    private String titre;
    private String texte;

    public Article(int auteur_id, String titre, String texte) {
        this.auteur_id = auteur_id;
        this.titre = titre;
        this.texte = texte;
    }

    public int getAuteur_id() { return auteur_id; }
    public String getTitre() { return titre; }
    public String getTexte() { return texte; }
    
}
