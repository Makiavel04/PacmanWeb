package com.pacman.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.pacman.beans.Cosmetique;

public class CosmetiqueDaoSQL implements CosmetiqueDao {
    
    private DAOFactorySQL daoFactory;

    public CosmetiqueDaoSQL(DAOFactorySQL daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void creer(Cosmetique cosmetique) throws DAOException {
        String sql = "INSERT INTO cosmetique (nom_cosmetique, couleur) VALUES (?, ?)";
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(sql)) {
            
            ps.setString(1, cosmetique.getNomCosmetique());
            ps.setString(2, cosmetique.getCouleur());
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la création du cosmétique.", e);
        }
    }

    @Override
    public List<Cosmetique> listerTous() throws DAOException {
        List<Cosmetique> cosmetiques = new ArrayList<>();
        String sql = "SELECT * FROM cosmetique";
        
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Cosmetique c = new Cosmetique();
                c.setId(rs.getInt("id"));
                c.setNomCosmetique(rs.getString("nom_cosmetique"));
                c.setCouleur(rs.getString("couleur"));
                cosmetiques.add(c);
            }
            
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la récupération de la boutique.", e);
        }
        return cosmetiques;
    }

    @Override
    public List<Cosmetique> listerInventaireJoueur(int idJoueur) throws DAOException {
        List<Cosmetique> inventaire = new ArrayList<>();
        
        String sql = "SELECT c.* FROM cosmetique c INNER JOIN a_cosmetique ac ON c.id = ac.id_cosmetique WHERE ac.id_joueur = ?";
        
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(sql)) {
            
            ps.setInt(1, idJoueur);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cosmetique c = new Cosmetique();
                    c.setId(rs.getInt("id"));
                    c.setNomCosmetique(rs.getString("nom_cosmetique"));
                    c.setCouleur(rs.getString("couleur"));
                    inventaire.add(c);
                }
            }
            
        } catch (SQLException e) {
            throw new DAOException("Erreur lors de la récupération de l'inventaire du joueur.", e);
        }
        return inventaire;
    }
}