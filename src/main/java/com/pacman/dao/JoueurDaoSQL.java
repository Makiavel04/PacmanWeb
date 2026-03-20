package com.pacman.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.pacman.beans.Joueur;

public class JoueurDaoSQL implements JoueurDao {
    
    private DAOFactorySQL daoFactory;

    public JoueurDaoSQL(DAOFactorySQL daoFactory) { 
        this.daoFactory = daoFactory; 
    }

    @Override
    public void creer(Joueur joueur) throws DAOException {
        String sql = "INSERT INTO joueur (pseudo, mot_de_passe, score_fantome, score_pacman) VALUES (?, ?, 0, 0)";
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(sql)) {
            ps.setString(1, joueur.getPseudo());
            ps.setString(2, joueur.getMotDePasse());
            ps.executeUpdate();
        } catch (SQLException e) { 
            throw new DAOException("Erreur lors de la création du joueur.", e); 
        }
    }

    @Override
    public Joueur trouver(String pseudo) throws DAOException {
        Joueur joueur = null;
        String sql = "SELECT * FROM joueur WHERE pseudo = ?";
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(sql)) {
            ps.setString(1, pseudo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    joueur = new Joueur();
                    joueur.setId(rs.getInt("id"));
                    joueur.setPseudo(rs.getString("pseudo"));
                    joueur.setMotDePasse(rs.getString("mot_de_passe"));
                    joueur.setScoreFantome(rs.getInt("score_fantome"));
                    joueur.setScorePacman(rs.getInt("score_pacman"));
                    joueur.setIdCosmetiqueActif(rs.getInt("id_cosmetique_actif")); // Vaut 0 si NULL dans MySQL
                }
            }
        } catch (SQLException e) { 
            throw new DAOException("Erreur lors de la recherche du joueur.", e); 
        }
        return joueur;
    }

    @Override
    public List<Joueur> listerMeilleursScores() throws DAOException {
        List<Joueur> joueurs = new ArrayList<>();
        String sql = "SELECT * FROM joueur ORDER BY score_pacman DESC LIMIT 10";
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Joueur joueur = new Joueur();
                joueur.setId(rs.getInt("id"));
                joueur.setPseudo(rs.getString("pseudo"));
                joueur.setScorePacman(rs.getInt("score_pacman"));
                joueur.setScoreFantome(rs.getInt("score_fantome"));
                joueurs.add(joueur);
            }
        } catch (SQLException e) { 
            throw new DAOException("Erreur lors de la récupération du classement.", e); 
        }
        return joueurs;
    }

    @Override
    public void ajouterCosmetique(int idJoueur, int idCosmetique) throws DAOException {
        String sql = "INSERT INTO a_cosmetique (id_joueur, id_cosmetique) VALUES (?, ?)";
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(sql)) {
            ps.setInt(1, idJoueur);
            ps.setInt(2, idCosmetique);
            ps.executeUpdate();
        } catch (SQLException e) { 
            throw new DAOException("Erreur lors de l'achat du cosmétique.", e); 
        }
    }

    @Override
    public void equiperCosmetique(int idJoueur, int idCosmetique) throws DAOException {
        String sql = "UPDATE joueur SET id_cosmetique_actif = ? WHERE id = ?";
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(sql)) {
            ps.setInt(1, idCosmetique);
            ps.setInt(2, idJoueur);
            ps.executeUpdate();
        } catch (SQLException e) { 
            throw new DAOException("Erreur lors de l'équipement du cosmétique.", e); 
        }
    }

    // NOUVEAU : La méthode pour l'API qui met à jour les scores !
    @Override
    public void mettreAJourScores(int idJoueur, int nouveauScorePacman, int nouveauScoreFantome) throws DAOException {
        // GREATEST() vérifie : "Garde l'ancien record, SAUF SI le nouveau score est plus grand"
        String sql = "UPDATE joueur SET score_pacman = GREATEST(score_pacman, ?), score_fantome = GREATEST(score_fantome, ?) WHERE id = ?";
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(sql)) {
            ps.setInt(1, nouveauScorePacman);
            ps.setInt(2, nouveauScoreFantome);
            ps.setInt(3, idJoueur);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la mise à jour des scores.", e);
        }
    }
}