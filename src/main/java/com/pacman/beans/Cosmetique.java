package com.pacman.beans;

public class Cosmetique {
    private String nom;
    private String description;
    private int prix; 

    public Cosmetique(String nom, String description, int prix) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPrix() { return prix; }
    public void setPrix(int prix) { this.prix = prix; }
}