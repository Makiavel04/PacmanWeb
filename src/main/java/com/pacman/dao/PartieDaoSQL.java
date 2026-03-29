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
    public Integer enregistrerPartie(Partie partie) throws DAOException {
        String sqlPartie = "INSERT INTO partie (score_pacmans, score_fantomes, vainqueur) VALUES (?, ?, ?)";
        int idPartie = -1;
        Connection connection = null;
        try {
        	connection = daoFactory.getConnection();
        	connection.setAutoCommit(false);
        	
        	idPartie = this.creerPartie(connection, partie);
        	
        	JoueurDaoSQL jDaoSql = (JoueurDaoSQL) this.daoFactory.getJoueurDao();//Nous sommes sur un DAO JDBC donc le cast est sûr
        	
        	if(partie.getIdPacmans()!=null) {
        		this.ajouterJoueurPartie(connection, partie.getIdPacmans(), idPartie, "P");
        		jDaoSql.updateScore(connection, partie.getIdPacmans(), partie.getScorePacmans(), "P");
        	}else throw new Exception("Les id pacmans n'existents pas.");

        	if(partie.getIdFantomes()!=null) {
        		this.ajouterJoueurPartie(connection, partie.getIdFantomes(), idPartie, "F");
        		jDaoSql.updateScore(connection, partie.getIdFantomes(), partie.getScoreFantomes(), "F");
        	}else throw new Exception("Les id fantomes n'existents pas.");
        	
        	connection.commit();
        } catch (Exception e) {
        	if (connection != null) { //Si erreur annule tout
                try { connection.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        	idPartie = -1;
            throw new DAOException("Erreur lors de l'enregistrement de la partie et de la liaison.", e);
        }finally {
	        if (connection != null) {
	            try { 
	            	connection.setAutoCommit(true); 
	            	connection.close(); 
	            } catch (SQLException e) { e.printStackTrace(); }
	        }
	    }
        return idPartie;
    }

	//Méthode propre au découpage SQL
	private Integer creerPartie(Connection connection, Partie partie) throws DAOException{
        String sqlPartie = "INSERT INTO partie (score_pacmans, score_fantomes, vainqueur) VALUES (?, ?, ?)";
        int idPartie = -1;
        try(PreparedStatement psPartie = connection.prepareStatement(sqlPartie, Statement.RETURN_GENERATED_KEYS)){
        	psPartie.setInt(1, partie.getScorePacmans());
            psPartie.setInt(2, partie.getScoreFantomes());
            psPartie.setString(3, partie.getVainqueur());
            psPartie.executeUpdate();
            try (ResultSet clesGenerees = psPartie.getGeneratedKeys()) {
                if (clesGenerees.next()) {
                    idPartie = clesGenerees.getInt(1);
                }else throw new SQLException("Échec de la récupération de l'id de la partie");
            }
        }catch(SQLException e) {
        	throw new DAOException("Erreur lors de la création de la partie.");
        }
    	return idPartie;
    }
    
	//Méthode propre au découpage SQL
    private void ajouterJoueurPartie(Connection connection, List<Integer> idJoueurs, Integer idPartie, String role) throws DAOException{
        String sqlLiaison = "INSERT INTO joue_partie (id_joueur, id_partie, role_joueur) VALUES (?, ?, ?)";
        try (PreparedStatement psLien = connection.prepareStatement(sqlLiaison)) {
            for (Integer j : idJoueurs) {
            	psLien.setInt(1, j);
            	psLien.setInt(2, idPartie);
            	psLien.setString(3, role);
            	psLien.addBatch(); // On ajoute la ligne au "lot" sans l'envoyer de suite
            }
            psLien.executeBatch(); // On envoie tout d'un coup à la base
        }catch(SQLException e) {
	    	throw new DAOException("Erreur lors de la création de la partie.");
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