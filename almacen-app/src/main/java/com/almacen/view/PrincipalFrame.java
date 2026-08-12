package com.almacen.view;

import com.almacen.model.Usuario;

import javax.swing.*;
import java.awt.*;

// Pantalla principal mostrada tras un login exitoso.

public class PrincipalFrame extends JFrame {

    private final Usuario usuarioActual;

    public PrincipalFrame(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;

        setTitle("Sistema de Gestión de Almacén - Bienvenido " + usuarioActual.getNombre());
        setSize(500, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        panelSuperior.add(btnCerrarSesion);

        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 30, 10));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JButton btnUsuarios = crearBotonIcono("\uD83D\uDC64", "Usuarios");
        JButton btnProductos = crearBotonIcono("\uD83D\uDCE6", "Productos");

        panelCentral.add(btnUsuarios);
        panelCentral.add(btnProductos);

        setLayout(new BorderLayout());
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);

        btnUsuarios.addActionListener(e -> {
            new GestionUsuariosFrame(this).setVisible(true);
            setVisible(false);
        });

        btnProductos.addActionListener(e -> {
            new GestionProductosFrame(this).setVisible(true);
            setVisible(false);
        });

        btnCerrarSesion.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }

    private JButton crearBotonIcono(String emoji, String texto) {
        JButton boton = new JButton("<html><center><span style='font-size:36px'>" + emoji
                + "</span><br>" + texto + "</center></html>");
        boton.setBackground(new Color(174, 198, 235));
        boton.setFocusPainted(false);
        return boton;
    }
}
