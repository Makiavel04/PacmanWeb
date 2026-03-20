package com.pacman.beans;

public class Partie {
    private int id;
    private int scorePacmans;
    private int scoreFantomes;
    private String vainqueur; // p ou f


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScorePacmans() {
        return scorePacmans;
    }

    public void setScorePacmans(int scorePacmans) {
        this.scorePacmans = scorePacmans;
    }

    public int getScoreFantomes() {
        return scoreFantomes;
    }

    public void setScoreFantomes(int scoreFantomes) {
        this.scoreFantomes = scoreFantomes;
    }

    public String getVainqueur() {
        return vainqueur;
    }

    public void setVainqueur(String vainqueur) {
        this.vainqueur = vainqueur;
    }
}