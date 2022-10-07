package fr.fs.sdbm.service;

import fr.fs.sdbm.metier.Couleur;
import fr.fs.sdbm.metier.Marque;
import fr.fs.sdbm.metier.TypeBiere;

public class ArticleSearch extends MarqueSearch{
    private int id;
    private String libelle;
    private int volume;
    private float titrage;
    private Float prixAchat;
    private Marque marque;
    private Couleur couleur;
    private TypeBiere typeBiere;

    public ArticleSearch() {
        marque = new Marque();
        couleur = new Couleur();
        typeBiere = new TypeBiere();


    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getLibelle() {
        return libelle;
    }

    @Override
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public float getTitrage() {
        return titrage;
    }

    public void setTitrage(float titrage) {
        this.titrage = titrage;
    }

    public Float getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(Float prixAchat) {
        this.prixAchat = prixAchat;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public TypeBiere getTypeBiere() {
        return typeBiere;
    }

    public void setTypeBiere(TypeBiere typeBiere) {
        this.typeBiere = typeBiere;
    }
}
