package com.example.mi_examen_ces3.model;

import com.example.mi_examen_ces3.dto.DtoUsuario;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CRUD {

    Usuario create(DtoUsuario user) throws SQLException;

    public ArrayList<Usuario> all();

    public Usuario findById(int id) throws SQLException;

    Usuario update(Usuario user) throws SQLException;

    void delete(int userId) throws SQLException;
}
