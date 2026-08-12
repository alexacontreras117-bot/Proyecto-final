package com.almacen.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// PATRÓN DE DISEÑO - SINGLETON:

public class ConexionBD {

    // ====================
    // Base de datos LOCAL 
    // ====================
    private static final String URL =
            "jdbc:mysql://localhost:3306/almacenitlafinal?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "admin"; 

    private static ConexionBD instancia;
    private Connection conexion;

    private ConexionBD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión a la base de datos establecida correctamente.");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Punto unico de acceso a la instancia (esencia del patron Singleton). 
    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    // Devuelve la conexion activa, reconectando si estuviera cerrada. 
    public Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                instancia = new ConexionBD();
                return instancia.conexion;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }
}
