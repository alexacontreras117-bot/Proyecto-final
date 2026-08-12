package com.almacen.dao;

import com.almacen.model.Usuario;
import java.util.List;

// Patron de diseño
 
// Pilar POO abstraccion

public interface UsuarioDAO {

    boolean registrar(Usuario usuario);

    boolean actualizar(Usuario usuario);

    boolean eliminar(int idUser);

// Usado por el login: busca un usuario que coincida en usuario y contraseña. 
    Usuario buscarPorUsuarioYPassword(String userName, String password);

    boolean existeUsuario(String userName);

    List<Usuario> listarTodos();
}
