package com.almacen.view;

import com.almacen.dao.DAOFactory;
import com.almacen.dao.ProductoDAO;
import com.almacen.model.Producto;

import javax.swing.*;
import java.awt.*;

// Ventana emergente del producto. 

public class ProductoFormDialog extends JDialog {

    private final JTextField txtNombre = new JTextField(15);
    private final JTextField txtMarca = new JTextField(15);
    private final JTextField txtCategoria = new JTextField(15);
    private final JTextField txtPrecio = new JTextField(15);
    private final JTextField txtCantidad = new JTextField(15);

    private final ProductoDAO productoDAO = DAOFactory.getProductoDAO();
    private final Producto productoExistente; 

    public ProductoFormDialog(JFrame padre, Producto productoExistente) {
        super(padre, true);
        this.productoExistente = productoExistente;

        setTitle(productoExistente == null ? "Nuevo Producto" : "Editar Producto");
        setSize(360, 330);
        setLocationRelativeTo(padre);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int fila = 0;
        fila = agregarCampo(panel, gbc, fila, "Nombre:", txtNombre);
        fila = agregarCampo(panel, gbc, fila, "Marca:", txtMarca);
        fila = agregarCampo(panel, gbc, fila, "Categoría:", txtCategoria);
        fila = agregarCampo(panel, gbc, fila, "Precio:", txtPrecio);
        fila = agregarCampo(panel, gbc, fila, "Cantidad Disponible:", txtCantidad);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnGuardar = new JButton("\uD83D\uDCBE Guardar");
        panelBotones.add(btnGuardar);

        if (productoExistente != null) {
            JButton btnEliminar = new JButton("\u274C Eliminar");
            panelBotones.add(btnEliminar);
            btnEliminar.addActionListener(e -> eliminar());
        }

        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);

        setContentPane(panel);

        if (productoExistente != null) {
            txtNombre.setText(productoExistente.getNombreProducto());
            txtMarca.setText(productoExistente.getMarcaProducto());
            txtCategoria.setText(productoExistente.getCategoriaProducto());
            txtPrecio.setText(String.valueOf(productoExistente.getPrecioProducto()));
            txtCantidad.setText(String.valueOf(productoExistente.getStockProducto()));
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
        String marca = txtMarca.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String cantidadTexto = txtCantidad.getText().trim();

        if (nombre.isEmpty() || marca.isEmpty() || categoria.isEmpty()
                || precioTexto.isEmpty() || cantidadTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        int precio;
        int cantidad;
        try {
            precio = Integer.parseInt(precioTexto);
            cantidad = Integer.parseInt(cantidadTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio y Cantidad Disponible deben ser números.");
            return;
        }

        boolean exito;
        if (productoExistente == null) {
            Producto nuevo = new Producto(nombre, marca, categoria, precio, cantidad);
            exito = productoDAO.registrar(nuevo);
        } else {
            productoExistente.setNombreProducto(nombre);
            productoExistente.setMarcaProducto(marca);
            productoExistente.setCategoriaProducto(categoria);
            productoExistente.setPrecioProducto(precio);
            productoExistente.setStockProducto(cantidad);
            exito = productoDAO.actualizar(productoExistente);
        }

        if (exito) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al guardar el producto.");
        }
    }

    private void eliminar() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar este producto?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            productoDAO.eliminar(productoExistente.getIdProducto());
            dispose();
        }
    }
}
