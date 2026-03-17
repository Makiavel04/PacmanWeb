package com.pacman.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.pacman.beans.Joueur;

public class JoueurDaoImpl implements JoueurDao {
    private DAOFactory daoFactory;

    JoueurDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Joueur trouver(String pseudo) throws DAOException {
        Joueur joueur = null;
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement("SELECT * FROM joueur WHERE pseudo = ?")) {
            ps.setString(1, pseudo);
            try (ResultSet resultat = ps.executeQuery()) {
                if (resultat.next()) {
                    joueur = new Joueur();
                    joueur.setId(resultat.getLong("id"));
                    joueur.setPseudo(resultat.getString("pseudo"));
                    joueur.setMotDePasse(resultat.getString("mot_de_passe"));
                    joueur.setPacgommes(resultat.getInt("pacgommes"));
                    joueur.setMeilleurScore(resultat.getInt("meilleur_score"));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur de recherche dans la base.", e);
        }
        return joueur;
    }

    @Override
    public void creer(Joueur joueur) throws DAOException {
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement("INSERT INTO joueur (pseudo, mot_de_passe, pacgommes, meilleur_score) VALUES (?, ?, 0, 0)")) {
            ps.setString(1, joueur.getPseudo());
            ps.setString(2, joueur.getMotDePasse());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la création du joueur.", e);
        }
    }

    @Override
    public List<Joueur> getMeilleursScores() throws DAOException {
        List<Joueur> joueurs = new ArrayList<>();
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement("SELECT * FROM joueur ORDER BY meilleur_score DESC LIMIT 10");
             ResultSet resultat = ps.executeQuery()) {
            while (resultat.next()) {
                Joueur joueur = new Joueur();
                joueur.setPseudo(resultat.getString("pseudo"));
                joueur.setMeilleurScore(resultat.getInt("meilleur_score"));
                joueurs.add(joueur);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la récupération des scores.", e);
        }
        return joueurs;
    }
}