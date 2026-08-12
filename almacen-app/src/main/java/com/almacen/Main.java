package com.almacen;

import com.almacen.view.LoginFrame;

import javax.swing.*;

// Punto de entrada de la aplicación "Sistema de Gestion de Productos de Almacen".

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
    }
}
