package com.almacen.view;

import com.almacen.dao.DAOFactory;
import com.almacen.dao.UsuarioDAO;
import com.almacen.model.Usuario;

import javax.swing.*;
import java.awt.*;

// Pantalla de inicio de sesion (LOGIN).
 
public class LoginFrame extends JFrame {

    private final JTextField txtUsuario = new JTextField(15);
    private final JPasswordField txtPassword = new JPasswordField(15);
    private final UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();

    public LoginFrame() {
        setTitle("Login - Sistema de Gestión de Almacén");
        setSize(380, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(new Color(174, 198, 235));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("LOGIN");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelPrincipal.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Nombre de Usuario:"), gbc);
        gbc.gridx = 1;
        panelPrincipal.add(txtUsuario, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        panelPrincipal.add(txtPassword, gbc);

        JButton btnEntrar = new JButton("Entrar");
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        panelPrincipal.add(btnEntrar, gbc);

        JButton btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setBorderPainted(false);
        btnRegistrarse.setContentAreaFilled(false);
        btnRegistrarse.setForeground(Color.BLUE.darker());
        gbc.gridy = 4;
        panelPrincipal.add(btnRegistrarse, gbc);

        setContentPane(panelPrincipal);

        btnEntrar.addActionListener(e -> autenticar());
        btnRegistrarse.addActionListener(e -> {
            new RegistroFrame().setVisible(true);
            dispose();
        });
    }

    // Requisito (valida campos vacios y credenciales contra la base de datos). 
    private void autenticar() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar su usuario y contraseña, si no está registrado debe registrarse.",
                    "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario u = usuarioDAO.buscarPorUsuarioYPassword(usuario, password);
        if (u != null) {
            new PrincipalFrame(u).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Usuario o contraseña incorrectos.",
                    "Error de autenticación", JOptionPane.ERROR_MESSAGE);
        }
    }
}
