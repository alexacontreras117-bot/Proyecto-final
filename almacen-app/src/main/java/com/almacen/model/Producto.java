package com.almacen.model;

// Representa un producto del almacen.
 
 // Pilar POO ENCAPSULAMIENTO: Todos los atributos son privados, el acceso externo se hace unicamente a traves de los metodos (getter) y (setter).
 
public class Producto {

    private int idProducto;
    private String nombreProducto;
    private String marcaProducto;
    private String categoriaProducto;
    private int precioProducto;
    private int stockProducto;

 // Constructor completo (producto ya existente en la base de datos). 
    public Producto(int idProducto, String nombreProducto, String marcaProducto,
                     String categoriaProducto, int precioProducto, int stockProducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.marcaProducto = marcaProducto;
        this.categoriaProducto = categoriaProducto;
        this.precioProducto = precioProducto;
        this.stockProducto = stockProducto;
    }

// Constructor para registrar un producto nuevo (aun sin id asignado por la BD). 
    public Producto(String nombreProducto, String marcaProducto,
                     String categoriaProducto, int precioProducto, int stockProducto) {
        this(0, nombreProducto, marcaProducto, categoriaProducto, precioProducto, stockProducto);
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getMarcaProducto() {
        return marcaProducto;
    }

    public void setMarcaProducto(String marcaProducto) {
        this.marcaProducto = marcaProducto;
    }

    public String getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(String categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    public int getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(int precioProducto) {
        this.precioProducto = precioProducto;
    }

    public int getStockProducto() {
        return stockProducto;
    }

    public void setStockProducto(int stockProducto) {
        this.stockProducto = stockProducto;
    }

    @Override
    public String toString() {
        return nombreProducto + " - " + marcaProducto;
    }
}
