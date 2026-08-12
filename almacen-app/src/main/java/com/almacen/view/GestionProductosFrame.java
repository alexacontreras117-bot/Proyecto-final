package com.almacen.view;

import com.almacen.dao.DAOFactory;
import com.almacen.dao.ProductoDAO;
import com.almacen.model.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

// Pantalla "Productos de Almacen".

public class GestionProductosFrame extends JFrame {

    private final JFrame framePadre;
    private final ProductoDAO productoDAO = DAOFactory.getProductoDAO();

    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Marca", "Categoría", "Precio", "Cantidad Disponible"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable tabla = new JTable(modelo);

    public GestionProductosFrame(JFrame framePadre) {
        this.framePadre = framePadre;

        setTitle("Productos de Almacén");
        setSize(720, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblTitulo = new JLabel("Productos de Almacén", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        JScrollPane scroll = new JScrollPane(tabla);

        JButton btnNuevo = new JButton("NUEVO");
        JButton btnVolver = new JButton("\u2190 Volver");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.add(btnNuevo);
        panelBotones.add(btnVolver);

        setLayout(new BorderLayout(10, 10));
        add(lblTitulo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarProductos();

        btnNuevo.addActionListener(e -> {
            new ProductoFormDialog(this, null).setVisible(true);
            cargarProductos();
        });

        btnVolver.addActionListener(e -> {
            framePadre.setVisible(true);
            dispose();
        });

 // Requisito (al hacer clic en un producto del listado se abre su formulario).
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    Producto seleccionado = obtenerSeleccionado();
                    if (seleccionado != null) {
                        new ProductoFormDialog(GestionProductosFrame.this, seleccionado).setVisible(true);
                        cargarProductos();
                    }
                }
            }
        });
    }

 // Vuelve a consultar la base de datos y refresca la tabla automaticamente. 
    public void cargarProductos() {
        modelo.setRowCount(0);
        List<Producto> productos = productoDAO.listarTodos();
        for (Producto p : productos) {
            modelo.addRow(new Object[]{
                    p.getIdProducto(), p.getNombreProducto(), p.getMarcaProducto(),
                    p.getCategoriaProducto(), p.getPrecioProducto(), p.getStockProducto()
            });
        }
    }

    private Producto obtenerSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return null;
        int id = (int) modelo.getValueAt(fila, 0);
        return productoDAO.listarTodos().stream()
                .filter(p -> p.getIdProducto() == id)
                .findFirst()
                .orElse(null);
    }
}
