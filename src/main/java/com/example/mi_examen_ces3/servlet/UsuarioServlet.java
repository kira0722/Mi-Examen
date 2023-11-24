package com.example.mi_examen_ces3.servlet;

import com.example.mi_examen_ces3.controller.CtrUsuario;
import com.example.mi_examen_ces3.dto.DtoUsuario;



import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import java.io.BufferedReader;
import java.util.ArrayList;



@WebServlet(name = "UsuarioServlet", value = "/usuario")
public class UsuarioServlet extends HelloServlet {
    private String message;

    private GsonBuilder gsonBuilder;

    private Gson gson;

    private ArrayList<DtoUsuario> users;

    CtrUsuario ctr = new CtrUsuario();

    public void init() {
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        users = new ArrayList<>();

        DtoUsuario user1 = new DtoUsuario();
        user1.id = 1;
        user1.setNombre("kira");
        user1.setCorreo("kira@gmail.com");

        users.add(user1);

        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i));
        }
        message = "Hello";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");
        JsonObject body = this.getParamsFromPost(req);

        int id = body.has("id") ? body.get("id").getAsInt() : 0; // Puedes establecer un valor predeterminado según tus necesidades
        String correo = body.has("correo") ? body.get("correo").getAsString() : "";
        String nombre = body.has("nombre") ? body.get("nombre").getAsString() : "";
        String password = body.has("password") ? body.get("password").getAsString() : "";

        DtoUsuario std = new DtoUsuario(id, correo, nombre, password);


        DtoUsuario newUser = ctr.addUser(std);

        out.print(gson.toJson(newUser));
        out.flush();


    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");
        String userIdParam = req.getParameter("id");

        if (userIdParam != null && !userIdParam.isEmpty()) {
            int userId = Integer.parseInt(userIdParam);
            DtoUsuario user = ctr.getUserById(userId);
            out.print(gson.toJson(user));
        } else {
            ArrayList<DtoUsuario> users = ctr.getAllUsers();
            out.print(gson.toJson(users));
        }

        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");

        int userId = Integer.parseInt(req.getParameter("id"));

        ctr.deleteUser(userId);

        out.print(gson.toJson("Eliminado"));
        out.flush();
    }

}