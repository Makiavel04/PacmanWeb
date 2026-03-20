package com.pacman.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactorySQL extends DAOFactory {
    private static final String FICHIER_PROPERTIES = "/com/pacman/dao/dao.properties";
    private String url;
    private String username;
    private String password;

    public DAOFactorySQL() {
        Properties properties = new Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream fichierProperties = classLoader.getResourceAsStream(FICHIER_PROPERTIES);
            
            if (fichierProperties == null) {
                throw new DAOConfigurationException("Le fichier properties est introuvable.");
            }
            
            properties.load(fichierProperties);
            this.url = properties.getProperty("url");
            this.username = properties.getProperty("nomutilisateur");
            this.password = properties.getProperty("motdepasse");
            Class.forName(properties.getProperty("driver"));
            
        } catch (Exception e) {
            throw new DAOConfigurationException("Impossible de charger le properties MySQL", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public JoueurDao getJoueurDao() {
        return new JoueurDaoSQL(this);
    }

    @Override
    public PartieDao getPartieDao() {
        return new PartieDaoSQL(this);
    }

    @Override
    public CosmetiqueDao getCosmetiqueDao() {
        return new CosmetiqueDaoSQL(this);
    }

}