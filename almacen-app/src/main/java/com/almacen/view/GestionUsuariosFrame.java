package com.almacen.view;

import com.almacen.dao.DAOFactory;
import com.almacen.dao.UsuarioDAO;
import com.almacen.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Pantalla de "Clientes Registrados" (gestion de usuarios).

public class GestionUsuariosFrame extends JFrame {

    private final JFrame framePadre;
    private final UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();

    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Apellido", "Teléfono", "Correo electrónico", "Usuario"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable tabla = new JTable(modelo);

    public GestionUsuariosFrame(JFrame framePadre) {
        this.framePadre = framePadre;

        setTitle("Clientes Registrados");
        setSize(720, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblTitulo = new JLabel("Clientes Registrados", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        JScrollPane scroll = new JScrollPane(tabla);

        JButton btnNuevo = new JButton("NUEVO");
        JButton btnActualizar = new JButton("ACTUALIZAR");
        JButton btnEliminar = new JButton("ELIMINAR");
        JButton btnVolver = new JButton("\u2190 Volver");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.add(btnNuevo);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);

        setLayout(new BorderLayout(10, 10));
        add(lblTitulo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarUsuarios();

        btnNuevo.addActionListener(e -> {
            new RegistroUsuarioDialog(this, null).setVisible(true);
            cargarUsuarios();
        });

        btnActualizar.addActionListener(e -> {
            Usuario seleccionado = obtenerSeleccionado();
            if (seleccionado == null) {
                JOptionPane.showMessageDialog(this, "Selecciona un usuario de la lista.");
                return;
            }
            new RegistroUsuarioDialog(this, seleccionado).setVisible(true);
            cargarUsuarios();
        });

        btnEliminar.addActionListener(e -> {
            Usuario seleccionado = obtenerSeleccionado();
            if (seleccionado == null) {
                JOptionPane.showMessageDialog(this, "Selecciona un usuario de la lista.");
                return;
            }
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que deseas eliminar a " + seleccionado.getNombre() + "?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                usuarioDAO.eliminar(seleccionado.getIdUser());
                cargarUsuarios();
            }
        });

        btnVolver.addActionListener(e -> {
            framePadre.setVisible(true);
            dispose();
        });
    }

    // Vuelve a consultar la base de datos y refresca la tabla automaticamente. 
    public void cargarUsuarios() {
        modelo.setRowCount(0);
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        for (Usuario u : usuarios) {
            modelo.addRow(new Object[]{
                    u.getIdUser(), u.getNombre(), u.getApellido(),
                    u.getTelefono(), u.getEmail(), u.getUserName()
            });
        }
    }

    private Usuario obtenerSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return null;
        int id = (int) modelo.getValueAt(fila, 0);
        return usuarioDAO.listarTodos().stream()
                .filter(u -> u.getIdUser() == id)
                .findFirst()
                .orElse(null);
    }
}
