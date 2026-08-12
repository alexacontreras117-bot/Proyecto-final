package com.almacen.dao;

// Patron de diseño (DAO)  FACTORY (Fábrica):

public class DAOFactory {

    public static UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAOImpl();
    }

    public static ProductoDAO getProductoDAO() {
        return new ProductoDAOImpl();
    }
}
