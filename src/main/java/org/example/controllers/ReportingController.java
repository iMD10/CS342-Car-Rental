package org.example.controllers;

import org.example.common.DatabaseHandler;
import org.example.common.ErrorHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportingController {
    private DatabaseHandler db ;
    public double getRevenue() {
        String query = "SELECT SUM(total_price) AS revenue FROM invoice;";
        db = new DatabaseHandler();
        try(ResultSet rs = db.executeQuery(query)) {
            if (rs.next()){
                return rs.getDouble("revenue");
            }
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Failed to get revenue");
        }finally {
        db.closeConnection();
        }
        return -1;
    }
    public int getTotalBookings() {
        String query = "SELECT COUNT(id) AS total FROM booking;";
        db = new DatabaseHandler();
        try(ResultSet rs = db.executeQuery(query)) {
            if (rs.next()){
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Failed to get total bookings");
        }finally {
        db.closeConnection();
        }
        return -1;
    }
    public int getTotalActive() {
        db = new DatabaseHandler();
        String query = "SELECT COUNT(id) AS total FROM booking WHERE status = 'active';";
        try(ResultSet rs = db.executeQuery(query)) {
            if (rs.next()){
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Failed to get total active bookings");
        }finally {
        db.closeConnection();
        }
        return -1;
    }
    public int getTotalCancelled() {
        String query = "SELECT COUNT(id) AS total FROM booking WHERE status = 'CANCELD';";
        db = new DatabaseHandler();
        try(ResultSet rs = db.executeQuery(query)) {
            if (rs.next()){
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Failed to get total cancelled bookings");
        }finally {
        db.closeConnection();
        }
        return -1;
    }
    public int getTotalReturned() {
        String query = "SELECT COUNT(id) AS total FROM booking WHERE status = 'RETURNED';";
        db = new DatabaseHandler();
        try(ResultSet rs = db.executeQuery(query)) {
            if (rs.next()){
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Failed to get total returned bookings");
        }finally {
        db.closeConnection();
        }
        return -1;
    }
    public int getTotalCustomer() {
        String query = "SELECT COUNT(id) AS total FROM users WHERE is_admin = false;";
        db = new DatabaseHandler();
        try(ResultSet rs = db.executeQuery(query)) {
            if (rs.next()){
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Failed to get total number of our customers");
        }finally {
        db.closeConnection();
        }
        return -1;
    }
}
