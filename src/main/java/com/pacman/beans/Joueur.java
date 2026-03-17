package com.pacman.beans;

public class Joueur {
    private Long id;
    private String pseudo;
    private String motDePasse;
    private int pacgommes;
    private int meilleurScore;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public int getPacgommes() { return pacgommes; }
    public void setPacgommes(int pacgommes) { this.pacgommes = pacgommes; }
    public int getMeilleurScore() { return meilleurScore; }
    public void setMeilleurScore(int meilleurScore) { this.meilleurScore = meilleurScore; }
}