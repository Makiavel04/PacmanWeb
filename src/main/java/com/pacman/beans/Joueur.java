package com.pacman.beans;

public class Joueur {
    private Integer id;
    private String pseudo;
    private String motDePasse;
    private Integer scoreFantome;
    private Integer scorePacman;
    private Integer idCosmetiqueActif;
    private Cosmetique cosmetiqueActif;

    public Integer getId() {
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

    public Integer getScoreFantome() {
        return scoreFantome;
    }

    public void setScoreFantome(int scoreFantome) {
        this.scoreFantome = scoreFantome;
    }

    public Integer getScorePacman() {
        return scorePacman;
    }

    public void setScorePacman(int scorePacman) {
        this.scorePacman = scorePacman;
    }

    public Integer getIdCosmetiqueActif() {
        return idCosmetiqueActif;
    }

    public void setIdCosmetiqueActif(int idCosmetiqueActif) {
        this.idCosmetiqueActif = idCosmetiqueActif;
    }

	public Cosmetique getCosmetiqueActif() {
		return cosmetiqueActif;
	}

	public void setCosmetiqueActif(Cosmetique cosmetiqueActif) {
		this.cosmetiqueActif = cosmetiqueActif;
	}
    
}