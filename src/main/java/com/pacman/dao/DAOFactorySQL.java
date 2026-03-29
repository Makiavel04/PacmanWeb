package com.pacman.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactorySQL extends DAOFactory {
	private static DAOFactorySQL dao = null;
	
    private static final String FICHIER_PROPERTIES = "/com/pacman/dao/dao.properties";
    private static final String PROPERTY_URL             = "url";
    private static final String PROPERTY_DRIVER          = "driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "nomutilisateur";
    private static final String PROPERTY_MOT_DE_PASSE    = "motdepasse";
    private String url;
    private String username;
    private String password;

    private DAOFactorySQL(String url, String username, String password) {
    	this.url = url;
        this.username = username;
        this.password = password;
    }
    
    public static DAOFactory getInstance() {
    	if(dao == null) {//Fait tout le chargement du fichier ici, pour eviter les exeception dans le constructeur
    		Properties properties = new Properties();
            String url;
            String driver;
            String username;
            String password;

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );

            if ( fichierProperties == null ) {
                throw new DAOConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
            }

            try {
                properties.load( fichierProperties );
                url = properties.getProperty( PROPERTY_URL );
                driver = properties.getProperty( PROPERTY_DRIVER );
                username = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
                password = properties.getProperty( PROPERTY_MOT_DE_PASSE );
            } catch ( IOException e ) {
                throw new DAOConfigurationException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
            }

            try {
                Class.forName( driver );
            } catch ( ClassNotFoundException e ) {
                throw new DAOConfigurationException( "Le driver est introuvable dans le classpath.", e );
            }

            DAOFactory instance = new DAOFactorySQL( url, username, password );

    		dao = new DAOFactorySQL(url, username, password);
    	}
    	return dao;
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