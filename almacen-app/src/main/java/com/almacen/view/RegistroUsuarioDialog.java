package com.almacen.view;

import com.almacen.dao.DAOFactory;
import com.almacen.dao.UsuarioDAO;
import com.almacen.model.Usuario;

import javax.swing.*;
import java.awt.*;

// Ventana emergente para crear un nuevo usuario o editar uno existente.

public class RegistroUsuarioDialog extends JDialog {

    private final JTextField txtUserName = new JTextField(15);
    private final JTextField txtNombre = new JTextField(15);
    private final JTextField txtApellido = new JTextField(15);
    private final JTextField txtTelefono = new JTextField(15);
    private final JTextField txtEmail = new JTextField(15);
    private final JPasswordField txtPassword = new JPasswordField(15);

    private final UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();
    private final Usuario usuarioExistente; // null => se está creando uno nuevo

    public RegistroUsuarioDialog(JFrame padre, Usuario usuarioExistente) {
        super(padre, true);
        this.usuarioExistente = usuarioExistente;

        setTitle(usuarioExistente == null ? "Nuevo Usuario" : "Editar Usuario");
        setSize(380, 380);
        setLocationRelativeTo(padre);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int fila = 0;
        fila = agregarCampo(panel, gbc, fila, "Nombre:", txtNombre);
        fila = agregarCampo(panel, gbc, fila, "Apellido:", txtApellido);
        fila = agregarCampo(panel, gbc, fila, "Usuario:", txtUserName);
        fila = agregarCampo(panel, gbc, fila, "Teléfono:", txtTelefono);
        fila = agregarCampo(panel, gbc, fila, "Correo:", txtEmail);
        fila = agregarCampo(panel, gbc, fila, "Contraseña:", txtPassword);

        JButton btnGuardar = new JButton("Guardar");
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
        panel.add(btnGuardar, gbc);

        setContentPane(panel);

        if (usuarioExistente != null) {
            txtNombre.setText(usuarioExistente.getNombre());
            txtApellido.setText(usuarioExistente.getApellido());
            txtUserName.setText(usuarioExistente.getUserName());
            txtTelefono.setText(usuarioExistente.getTelefono());
            txtEmail.setText(usuarioExistente.getEmail());
            txtPassword.setText(usuarioExistente.getPassword());
        }

        btnGuardar.addActionListener(e -> guardar());
    }

    private int agregarCampo(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent campo) {
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel(etiqueta), gbc);
        gbc.gridx = 1;
        panel.add(campo, gbc);
        return fila + 1;
    }

    private void guardar() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String userName = txtUserName.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (nombre.isEmpty() || apellido.isEmpty() || userName.isEmpty()
                || telefono.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        boolean exito;
        if (usuarioExistente == null) {
            Usuario nuevo = new Usuario(userName, nombre, apellido, telefono, email, password);
            exito = usuarioDAO.registrar(nuevo);
        } else {
            usuarioExistente.setNombre(nombre);
            usuarioExistente.setApellido(apellido);
            usuarioExistente.setUserName(userName);
            usuarioExistente.setTelefono(telefono);
            usuarioExistente.setEmail(email);
            usuarioExistente.setPassword(password);
            exito = usuarioDAO.actualizar(usuarioExistente);
        }

        if (exito) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al guardar el usuario.");
        }
    }
}
