package org.example.controllers;

import org.example.classes.User;
import org.example.common.DatabaseHandler;
import org.example.common.ErrorHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    private DatabaseHandler db;

    public User loginUser(String email, String password) {
        db = new DatabaseHandler();
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (ResultSet rs = db.executeQuery(query, email, password)){

            if(rs.next() && rs != null) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getBoolean("is_admin")
                );
            } else {
                throw new RuntimeException("Email or password is incorrect");
            }
        } catch (SQLException | RuntimeException e) {
            ErrorHandler.handleException(e,e.getMessage());
        }
        finally {
            db.closeConnection();
        }
        return  null;
    }

    public User registerCustomer(String email, String fname, String lname, String phone, String password) {
        //Don't change users to user because we changed it in db!!!!
        String checkQuery = "SELECT * FROM users WHERE email = ?;";
        String insertQuery = "INSERT INTO users (email, name, phone, password, is_admin) VALUES (?, ?, ?, ?, false)";
        db = new DatabaseHandler();
        try (ResultSet rs = db.executeQuery(checkQuery, email)) {
            // Check if email already exists
            if (rs != null && rs.next()) {
                throw new RuntimeException("Email already in use");
            }

            // Combine first and last name
            String name = fname + " " + lname;

            // Insert the new user and get the generated ID
            int userId = db.executeUpdate(insertQuery, email, name, phone, password);

            // If successful, return the User object
            if (userId >= 0) {
                return new User(userId, name, email, phone, password, false);
            } else {
                throw new RuntimeException("Failed to insert new user into the database.");
            }
        } catch (RuntimeException e) {
            ErrorHandler.handleException(e, "A runtime error occurred: " + e.getMessage());
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "A database error occurred: " + e.getMessage());
        }
        finally {
            db.closeConnection();
        }

        return null; // Return null if registration fails
    }

    public List<User> getAllUsers() {
        String query = "select * from users";
        db = new DatabaseHandler();
        try(ResultSet rs = db.executeQuery(query)){
            List<User> users = new ArrayList<>();
            while(rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getBoolean("is_admin")
                ));
            }
            return users;

        } catch (SQLException e) {
            ErrorHandler.handleException(e, e.getMessage());
        }finally {
            db.closeConnection();
        }
        return null;
    }
    public User getUserById(int user_id) {
        db = new DatabaseHandler();
        String query = "select * from users where id = ?";
        try(ResultSet rs = db.executeQuery(query,user_id)){
            if(rs.next() && rs != null) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getBoolean("is_admin")
                );
            }
            else throw new SQLException("User not found");
        } catch (SQLException e) {
            ErrorHandler.handleException(e, e.getMessage());
        }
        finally {
            db.closeConnection();
        }

        return null;
    }
    public List<User> getCustomers() {
        String query = "select * from users where is_admin = false";
        db = new DatabaseHandler();
        try(ResultSet rs = db.executeQuery(query)){
            List<User> users = new ArrayList<>();
            while(rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getBoolean("is_admin")
                ));
            }
            return users;

        } catch (SQLException e) {
            ErrorHandler.handleException(e, e.getMessage());
        }finally {
            db.closeConnection();
        }
        return null;
    }
    public boolean updateCustomerInfo(String email, String firstName, String lastName, String phone, String password) {
        db = new DatabaseHandler();
        String name = firstName + " " + lastName;
        String query = "UPDATE users SET name = ?,phone = ?, password = ? WHERE email = ?";
        try {
            db.executeUpdate(query,name, phone, password, email);
            return true;
        }
        catch (SQLException e) {
            ErrorHandler.handleException(e, "Error updating user information"); return false;
        }
        finally {
            db.closeConnection();
        }
    }

}
