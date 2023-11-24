package com.example.mi_examen_ces3.controller;

import com.example.mi_examen_ces3.dto.DtoUsuario;
import com.example.mi_examen_ces3.model.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;

public class CtrUsuario {

    private Usuario modelUsuario;

    public CtrUsuario(){
        modelUsuario = new Usuario();
    }

    public DtoUsuario addUser(DtoUsuario student){
        try {
            Usuario newUser = modelUsuario.create(student);
            return new DtoUsuario(newUser.getId(), newUser.getCorreo(), newUser.getNombre(), newUser.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<DtoUsuario> getAllUsers() {
        try {
            ArrayList<Usuario> users = modelUsuario.all();
            ArrayList<DtoUsuario> dtoUsers = new ArrayList<>();

            for (Usuario user : users) {
                DtoUsuario dtoUser = new DtoUsuario(
                        user.getId(),
                        user.getCorreo(),
                        user.getNombre(),
                        user.getPassword()
                );
                dtoUsers.add(dtoUser);
            }

            return dtoUsers;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DtoUsuario getUserById(int userId) {
        try {
            Usuario user = modelUsuario.findById(userId);
            if (user != null) {
                return new DtoUsuario(user.getId(), user.getCorreo(), user.getNombre(), user.getPassword());
            } else {
                throw new RuntimeException("No existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(int userId) {
        try {
            modelUsuario.delete(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
