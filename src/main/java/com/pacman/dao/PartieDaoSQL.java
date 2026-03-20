package com.pacman.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.pacman.beans.Partie;

public class PartieDaoSQL implements PartieDao {
    private DAOFactorySQL daoFactory;

    public PartieDaoSQL(DAOFactorySQL daoFactory) { 
        this.daoFactory = daoFactory; 
    }

    @Override
    public void enregistrerPartie(int idJoueur, Partie partie) throws DAOException {
        String sqlPartie = "INSERT INTO partie (score_pacmans, score_fantomes, vainqueur) VALUES (?, ?, ?)";
        String sqlLiaison = "INSERT INTO joue_partie (id_joueur, id_partie) VALUES (?, ?)";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement psPartie = connexion.prepareStatement(sqlPartie, Statement.RETURN_GENERATED_KEYS)) {
            
            psPartie.setInt(1, partie.getScorePacmans());
            psPartie.setInt(2, partie.getScoreFantomes());
            psPartie.setString(3, partie.getVainqueur());
            psPartie.executeUpdate();

            try (ResultSet clesGenerees = psPartie.getGeneratedKeys()) {
                if (clesGenerees.next()) {
                    int idPartie = clesGenerees.getInt(1);

                    try (PreparedStatement psLiaison = connexion.prepareStatement(sqlLiaison)) {
                        psLiaison.setInt(1, idJoueur);
                        psLiaison.setInt(2, idPartie);
                        psLiaison.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de l'enregistrement de la partie et de la liaison.", e);
        }
    }

    @Override
    public List<Partie> listerPartiesDuJoueur(int idJoueur) throws DAOException {
        List<Partie> historique = new ArrayList<>();
        
        String sql = "SELECT p.* FROM partie p INNER JOIN joue_partie jp ON p.id = jp.id_partie WHERE jp.id_joueur = ?";
        
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(sql)) {
            
            ps.setInt(1, idJoueur);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Partie partie = new Partie();
                    partie.setId(rs.getInt("id"));
                    partie.setScorePacmans(rs.getInt("score_pacmans"));
                    partie.setScoreFantomes(rs.getInt("score_fantomes"));
                    partie.setVainqueur(rs.getString("vainqueur"));
                    historique.add(partie);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la récupération de l'historique des parties.", e);
        }
        return historique;
    }
}