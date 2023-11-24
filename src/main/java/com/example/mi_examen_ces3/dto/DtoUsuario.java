package com.example.mi_examen_ces3.dto;



public class DtoUsuario {

    public int id;
    protected String correo;
    private String nombre;
    protected String password;

    public DtoUsuario(int id, String correo, String nombre, String password){
        this.id = id;
        this.correo = correo;
        this.nombre = nombre;
        this.password = password;
    }

    public DtoUsuario(String correo, String nombre, String password){
        this.correo = correo;
        this.nombre = nombre;
        this.password = password;
    }

    public DtoUsuario() {
    }

    public int getId(){
        return this.id;
    }


    private void setId(int id){
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "El usuario se llama: " + this.nombre +
                " su correo es: " + this.correo;
    }
}
