package com.almacen.model;

// Representa un usuario registrado en el sistema.
 
// Pilar POO - HERENCIA: Usuario extiende de Persona, reutilizando los atributos y métodos

public class Usuario extends Persona {

    private int idUser;
    private String userName;
    private String telefono;
    private String email;
    private String password;

 // Constructor completo (usuario ya existente en la base de datos). 
    public Usuario(int idUser, String userName, String nombre, String apellido,
                    String telefono, String email, String password) {
        super(nombre, apellido);
        this.idUser = idUser;
        this.userName = userName;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
    }

// Constructor para registrar un usuario nuevo (aun sin id asignado por la BD). 
    public Usuario(String userName, String nombre, String apellido,
                    String telefono, String email, String password) {
        this(0, userName, nombre, apellido, telefono, email, password);
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Pilar POO - POLIMORFISMO: Un override del metodo abstracto declarado en Persona.

    @Override
    public String descripcion() {
        return getNombre() + " " + getApellido() + " (@" + userName + ") - " + email;
    }

    @Override
    public String toString() {
        return descripcion();
    }
}
