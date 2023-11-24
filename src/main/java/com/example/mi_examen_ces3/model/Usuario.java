package com.example.mi_examen_ces3.model;

import com.example.mi_examen_ces3.dto.DtoUsuario;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Usuario extends  connection2 implements CRUD{

    public int id;

    protected String correo;

    private String nombre;



    protected String password;

    public Usuario(int id, String correo, String nombre, String password){
        this.id = id;
        this.correo = correo;
        this.nombre = nombre;
        this.password = password;
    }

    public Usuario(String correo){
        this.correo = correo;
    }

    public Usuario() {
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
        return "El user se llama: " + this.nombre +
                " su correo es: " + this.correo;
    }

    @Override
    public Usuario create(DtoUsuario user) throws SQLException {
        Connection cnn = this.getConexion();
        if(cnn != null) {
            String sql = "INSERT INTO user(mail, nombre, password) VALUES('"+user.getCorreo()+"', '"+user.getNombre()+"','"+user.getPassword()+"')";
            this.correo = user.getCorreo();
            this.nombre = user.getNombre();
            this.password = user.getPassword();
            try {
                PreparedStatement stmt = cnn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                this.id = rs.getInt(1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                cnn.close();
            }
            return this;
        }
        return null;
    }

    @Override
    public ArrayList<Usuario> all() {
        Connection cnn = this.getConexion();
        ArrayList<Usuario> users = new ArrayList<>();

        if (cnn != null) {
            String sql = "SELECT id,correo,nombre,password FROM datos";
            try {
                PreparedStatement stmt = cnn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String correo = rs.getString("correo");
                    String password = rs.getString("password");
                    Usuario user = new Usuario(id, correo, nombre,password);
                    users.add(user);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (cnn != null) {
                        cnn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return users;
        }
        return null;
    }



    @Override
    public Usuario findById(int userId) throws SQLException {
        Connection cnn = getConexion();

        if (cnn != null) {
            String sql = "SELECT id,correo,nombre,password FROM datos WHERE id = ?";
            try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("id");
                        String correo = rs.getString("correo");
                        String nombre = rs.getString("nombre");
                        String password = rs.getString("password");
                        return new Usuario(id, correo, nombre,password);
                    } else {
                        return null;
                    }
                }
            } finally {
                if (cnn != null) {
                    cnn.close();
                }
            }
        }
        return null;
    }

    @Override
    public Usuario update(Usuario user) throws SQLException {
        Connection cnn = getConexion();

        if (cnn != null) {
            String sql = "UPDATE datos SET correo = ?, nombre = ?, password = ? WHERE id = ?";
            try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setString(1, user.getCorreo());
                stmt.setString(2, user.getNombre());
                stmt.setString(3, user.getPassword());
                stmt.setInt(4, user.getId());
                stmt.executeUpdate();
            } finally {
                if (cnn != null) {
                    cnn.close();
                }
            }
        }
        return user;
    }

    @Override
    public void delete(int userId) throws SQLException {
        Connection cnn = getConexion();

        if (cnn != null) {
            String sql = "DELETE FROM datos WHERE id = ?";
            try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            } finally {
                if (cnn != null) {
                    cnn.close();
                }
            }
        }
    }
}
