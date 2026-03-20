package com.pacman.beans;

public class Joueur {
    private int id;
    private String pseudo;
    private String motDePasse;
    private int scoreFantome;
    private int scorePacman;
    private int idCosmetiqueActif;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public int getScoreFantome() {
        return scoreFantome;
    }

    public void setScoreFantome(int scoreFantome) {
        this.scoreFantome = scoreFantome;
    }

    public int getScorePacman() {
        return scorePacman;
    }

    public void setScorePacman(int scorePacman) {
        this.scorePacman = scorePacman;
    }

    public int getIdCosmetiqueActif() {
        return idCosmetiqueActif;
    }

    public void setIdCosmetiqueActif(int idCosmetiqueActif) {
        this.idCosmetiqueActif = idCosmetiqueActif;
    }
}