package com.pacman.beans;

public class Cosmetique {
    private Integer id;
    private String nomCosmetique;
    private String couleur;

 
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomCosmetique() {
        return nomCosmetique;
    }

    public void setNomCosmetique(String nomCosmetique) {
        this.nomCosmetique = nomCosmetique;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
    
    @Override
    public boolean equals(Object o) {
    	if (this == o) return true;
    	if (o == null || getClass() != o.getClass()) return false;
    	Cosmetique other = (Cosmetique) o;
    	if (this.id == null || other.getId() == null) return false;

    	return this.id.equals(other.getId());
    }
}