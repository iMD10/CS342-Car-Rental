package org.example.controllers;

import org.example.classes.User;
import org.example.common.DatabaseHandler;
import org.example.common.ErrorHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {
    private DatabaseHandler db;
    public User loginUser(String email, String password) {

    }

    public User registerUser(String email,String fname, String lname,String phone, String password) {
        try{
            db = new DatabaseHandler();
            String query = "select * from users where email = ?";
            ResultSet rs = db.executeQuery(query, email);
            if (rs.lenght > 0)
                throw new RuntimeException("Email already in use");

            String cQuery = "insert into users where (email, name, phone, password, is_admin) values (?,?,?,?)";

        } catch (RuntimeException e) {
            ErrorHandler.handleException(e, "Email already in use");
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Failed to make query");
        }

        String name = fname +" "+ lname;

        User createdUser = new User(name, email, phone, password, false);

    }

}
