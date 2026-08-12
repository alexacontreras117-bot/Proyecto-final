package com.almacen.view;

import com.almacen.dao.DAOFactory;
import com.almacen.dao.UsuarioDAO;
import com.almacen.model.Usuario;

import javax.swing.*;
import java.awt.*;

// Pantalla de registro de un nuevo usuario.

public class RegistroFrame extends JFrame {

    private final JTextField txtUserName = new JTextField(15);
    private final JTextField txtNombre = new JTextField(15);
    private final JTextField txtApellido = new JTextField(15);
    private final JTextField txtTelefono = new JTextField(15);
    private final JTextField txtEmail = new JTextField(15);
    private final JPasswordField txtPassword = new JPasswordField(15);
    private final JPasswordField txtConfirmar = new JPasswordField(15);

    private final UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();

    public RegistroFrame() {
        setTitle("Registro de Usuario");
        setSize(420, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(174, 198, 235));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("REGISTRO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        int fila = 1;
        fila = agregarCampo(panel, gbc, fila, "Nombre:", txtNombre);
        fila = agregarCampo(panel, gbc, fila, "Apellido:", txtApellido);
        fila = agregarCampo(panel, gbc, fila, "Nombre de Usuario:", txtUserName);
        fila = agregarCampo(panel, gbc, fila, "Teléfono:", txtTelefono);
        fila = agregarCampo(panel, gbc, fila, "Correo Electrónico:", txtEmail);
        fila = agregarCampo(panel, gbc, fila, "Contraseña:", txtPassword);
        fila = agregarCampo(panel, gbc, fila, "Confirmar Contraseña:", txtConfirmar);

        JButton btnRegistrar = new JButton("Registrar");
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
        panel.add(btnRegistrar, gbc);

        JButton btnVolver = new JButton("Volver al Login");
        btnVolver.setBorderPainted(false);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setForeground(Color.BLUE.darker());
        gbc.gridy = fila + 1;
        panel.add(btnVolver, gbc);

        setContentPane(panel);

        btnRegistrar.addActionListener(e -> registrar());
        btnVolver.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }

    private int agregarCampo(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent campo) {
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = fila;
        panel.add(new JLabel(etiqueta), gbc);
        gbc.gridx = 1;
        panel.add(campo, gbc);
        return fila + 1;
    }

    // Requisitos de todos los campos son obligatorios. 

    private void registrar() {
        String userName = txtUserName.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());

        String campoFaltante = null;
        if (nombre.isEmpty()) campoFaltante = "Nombre";
        else if (apellido.isEmpty()) campoFaltante = "Apellido";
        else if (userName.isEmpty()) campoFaltante = "Nombre de Usuario";
        else if (telefono.isEmpty()) campoFaltante = "Teléfono";
        else if (email.isEmpty()) campoFaltante = "Correo Electrónico";
        else if (password.isEmpty()) campoFaltante = "Contraseña";
        else if (confirmar.isEmpty()) campoFaltante = "Confirmar Contraseña";

        if (campoFaltante != null) {
            JOptionPane.showMessageDialog(this,
                    "Falta completar el campo: " + campoFaltante,
                    "Campo obligatorio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirmar)) {
            JOptionPane.showMessageDialog(this,
                    "La contraseña y la confirmación de la contraseña no coinciden.",
                    "Error de contraseña", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (usuarioDAO.existeUsuario(userName)) {
            JOptionPane.showMessageDialog(this,
                    "Ese nombre de usuario ya está registrado, elige otro.",
                    "Usuario existente", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario nuevo = new Usuario(userName, nombre, apellido, telefono, email, password);
        if (usuarioDAO.registrar(nuevo)) {
            JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
            new LoginFrame().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Ocurrió un error al registrar el usuario.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
