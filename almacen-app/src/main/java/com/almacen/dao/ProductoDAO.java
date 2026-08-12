package com.almacen.dao;

import com.almacen.model.Producto;
import java.util.List;

//Patron de diseño (DAO) Contrato de operaciones para acceder a los datos de productos.
 
public interface ProductoDAO {

    boolean registrar(Producto producto);

    boolean actualizar(Producto producto);

    boolean eliminar(int idProducto);

    List<Producto> listarTodos();
}
