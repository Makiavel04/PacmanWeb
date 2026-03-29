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
    	int id_gen = -1;
    	Connection connexion = null;
    	try{
    		connexion = daoFactory.getConnection();
    		connexion.setAutoCommit(false);
    		
	        String sql_creerj = "INSERT INTO joueur (pseudo, mot_de_passe, score_fantome, score_pacman, id_cosmetique_actif) VALUES (?, ?, 0, 0, 1)";
	        try (PreparedStatement psJ = connexion.prepareStatement(sql_creerj, java.sql.Statement.RETURN_GENERATED_KEYS)) {
	        	psJ.setString(1, joueur.getPseudo());
	        	psJ.setString(2, joueur.getMotDePasse());
	        	psJ.executeUpdate();
	            try (ResultSet rs = psJ.getGeneratedKeys()) {
	                if (rs.next()) {
	                	id_gen = rs.getInt(1);
	                }
	            }
	        }
	        
	        if(id_gen != -1) {
		        String sql_cosj = "INSERT INTO a_cosmetique (id_joueur, id_cosmetique) VALUES (?, ?)";
		        try (PreparedStatement psC = connexion.prepareStatement(sql_cosj)) {
		        	psC.setInt(1, id_gen);
		        	psC.setInt(2, 1);
		        	psC.executeUpdate();
		        }
		        connexion.commit();
	        } else {
	            throw new SQLException("Impossible de récupérer l'ID généré.");
	        }
    	} catch (SQLException e) { 
    		if (connexion != null) { //Si erreur annule tout
                try { connexion.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
    		throw new DAOException("Erreur lors de la création du joueur.", e); 
	    }finally {
	        if (connexion != null) {
	            try { 
	                connexion.setAutoCommit(true); 
	                connexion.close(); 
	            } catch (SQLException e) { e.printStackTrace(); }
	        }
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

    public void updateScore(Connection connection, List<Integer> idJoueurs, Integer score, String role) throws DAOException{
    	if(idJoueurs == null) throw new DAOException("Liste de joueurs à mettre à jour vide");
    	String sqlUpScore;
    	if("P".equals(role)) sqlUpScore = "UPDATE joueur SET score_pacman = score_pacman + ? WHERE id = ?";
    	else if("F".equals(role)) sqlUpScore = "UPDATE joueur SET score_fantome = score_fantome + ? WHERE id = ?";
    	else throw new DAOException("Rôle inconnu : "+role);
    	
    	try(PreparedStatement psScore = connection.prepareStatement(sqlUpScore)){
    		for (Integer j : idJoueurs) {
    			psScore.setInt(1, score);
    			psScore.setInt(2, j);
    			psScore.addBatch(); // On ajoute la ligne au "lot" sans l'envoyer de suite
            }
    		psScore.executeBatch();
    	}catch (SQLException e) { 
            throw new DAOException("Erreur lors de la mise à jour du score.", e); 
        }
    }
}