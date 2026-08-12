package com.almacen.dao;

import com.almacen.db.ConexionBD;
import com.almacen.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Implementacion concreta de ProductoDAO usando JDBC contra la tabla "productos" de la base de datos "almacenitlafinal".
 
public class ProductoDAOImpl implements ProductoDAO {

    @Override
    public boolean registrar(Producto producto) {
        String sql = "INSERT INTO productos (NombreProducto, MarcaProducto, CategoriaProducto, "
                + "PrecioProducto, StockProducto) VALUES (?, ?, ?, ?, ?)";
        Connection con = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, producto.getNombreProducto());
            ps.setString(2, producto.getMarcaProducto());
            ps.setString(3, producto.getCategoriaProducto());
            ps.setInt(4, producto.getPrecioProducto());
            ps.setInt(5, producto.getStockProducto());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Producto producto) {
        String sql = "UPDATE productos SET NombreProducto = ?, MarcaProducto = ?, "
                + "CategoriaProducto = ?, PrecioProducto = ?, StockProducto = ? WHERE idProducto = ?";
        Connection con = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, producto.getNombreProducto());
            ps.setString(2, producto.getMarcaProducto());
            ps.setString(3, producto.getCategoriaProducto());
            ps.setInt(4, producto.getPrecioProducto());
            ps.setInt(5, producto.getStockProducto());
            ps.setInt(6, producto.getIdProducto());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int idProducto) {
        String sql = "DELETE FROM productos WHERE idProducto = ?";
        Connection con = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Producto> listarTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos ORDER BY idProducto";
        Connection con = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Producto(
                        rs.getInt("idProducto"),
                        rs.getString("NombreProducto"),
                        rs.getString("MarcaProducto"),
                        rs.getString("CategoriaProducto"),
                        rs.getInt("PrecioProducto"),
                        rs.getInt("StockProducto")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
