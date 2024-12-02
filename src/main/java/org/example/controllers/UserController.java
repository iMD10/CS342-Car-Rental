package org.example.controllers;

import org.example.classes.User;
import org.example.common.DatabaseHandler;
import org.example.common.ErrorHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {
    private DatabaseHandler db;
    public User loginUser(String email, String password) {
        try {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            db = new DatabaseHandler();
            db.executeQuery(query, email, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public User registerCustomer(String email,String fname, String lname,String phone, String password) {
        try{
            db = new DatabaseHandler();
            String query = "select * from users where email = ?";
            ResultSet rs = db.executeQuery(query, email);
            if (rs == null || !rs.next())
                throw new RuntimeException("Email already in use");

            String name = fname +" "+ lname;

            String cQuery = "insert into users where (email, name, phone, password, is_admin) values (?,?,?,?, false)";
            int rows = db.executeUpdate(cQuery, email, name, phone, password);
            if(rows > 0) {
                return new User(rows,name, email, phone, password, false);
            } else {
                throw new RuntimeException("Error inserting user");
            }

        } catch (RuntimeException e) {
            ErrorHandler.handleException(e, "Email already in use");
        } catch (SQLException e) {
            ErrorHandler.handleException(e, e.getMessage());
        }
        return null;

    }

}
