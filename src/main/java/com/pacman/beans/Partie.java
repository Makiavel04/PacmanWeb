package com.pacman.beans;

import java.util.List;

public class Partie {
    private Integer id;
    private Integer scorePacmans;
    private Integer scoreFantomes;
    private String vainqueur; // p ou f
    
    private List<Integer> idPacmans;
    private List<Integer> idFantomes;


    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getScorePacmans() {
        return scorePacmans;
    }

    public void setScorePacmans(int scorePacmans) {
        this.scorePacmans = scorePacmans;
    }

    public Integer getScoreFantomes() {
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

	public List<Integer> getIdFantomes() {
		return idFantomes;
	}

	public void setIdFantomes(List<Integer> idFantomes) {
		this.idFantomes = idFantomes;
	}

	public List<Integer> getIdPacmans() {
		return idPacmans;
	}

	public void setIdPacmans(List<Integer> idPacmans) {
		this.idPacmans = idPacmans;
	}
}