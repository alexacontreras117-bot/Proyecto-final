package com.almacen.dao;

import com.almacen.db.ConexionBD;
import com.almacen.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 Implementacion concreta de UsuarioDAO usando JDBC contra la tabla
 * "usuarios" de la base de datos "almacenitlafinal".
 *
 * PILAR POO - POLIMORFISMO: */
 
public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public boolean registrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (UserName, Nombre, Apellido, Telefono, Email, Password) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        Connection con = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario.getUserName());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getTelefono());
            ps.setString(5, usuario.getEmail());
            ps.setString(6, usuario.getPassword());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET UserName = ?, Nombre = ?, Apellido = ?, "
                + "Telefono = ?, Email = ?, Password = ? WHERE idUser = ?";
        Connection con = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario.getUserName());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getTelefono());
            ps.setString(5, usuario.getEmail());
            ps.setString(6, usuario.getPassword());
            ps.setInt(7, usuario.getIdUser());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int idUser) {
        String sql = "DELETE FROM usuarios WHERE idUser = ?";
        Connection con = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUser);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Usuario buscarPorUsuarioYPassword(String userName, String password) {
        String sql = "SELECT * FROM usuarios WHERE UserName = ? AND Password = ?";
        Connection con = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean existeUsuario(String userName) {
        String sql = "SELECT idUser FROM usuarios WHERE UserName = ?";
        Connection con = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userName);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY idUser";
        Connection con = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Convierte una fila del ResultSet en un objeto Usuario. 
    private Usuario mapear(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("idUser"),
                rs.getString("UserName"),
                rs.getString("Nombre"),
                rs.getString("Apellido"),
                rs.getString("Telefono"),
                rs.getString("Email"),
                rs.getString("Password")
        );
    }
}
