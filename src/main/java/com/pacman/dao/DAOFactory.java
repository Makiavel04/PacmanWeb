package com.pacman.dao;

public abstract class DAOFactory {
    public static final int MYSQL = 1;

    public abstract JoueurDao getJoueurDao();
    public abstract PartieDao getPartieDao();
    public abstract CosmetiqueDao getCosmetiqueDao();

    public static DAOFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL: 
                return new DAOFactorySQL();
            default: 
                return null;
        }
    }
}