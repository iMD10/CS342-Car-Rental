package org.example.controllers;

import org.example.common.DatabaseHandler;
import org.example.common.ErrorHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportingController {
    private DatabaseHandler db = new DatabaseHandler();
    public double getRevenue() {
        String query = "SELECT SUM(total_price) AS revenue FROM invoice;";
        try(ResultSet rs = db.executeQuery(query)) {
            if (rs.next()){
                return rs.getDouble("revenue");
            }
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Failed to get revenue");
        }
        return -1;
    }
    public int getTotalBookings() {
        String query = "SELECT COUNT(id) AS total FROM booking;";
        try(ResultSet rs = db.executeQuery(query)) {
            if (rs.next()){
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Failed to get total bookings");
        }
        return -1;
    }
    public int getTotalActive() {
        String query = "SELECT COUNT(id) AS total FROM booking WHERE status = 'active';";
        try(ResultSet rs = db.executeQuery(query)) {
            if (rs.next()){
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Failed to get total active bookings");
        }
        return -1;
    }
    public int getTotalCancelled() {
        String query = "SELECT COUNT(id) AS total FROM booking WHERE status = 'CANCELD';";
        try(ResultSet rs = db.executeQuery(query)) {
            if (rs.next()){
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Failed to get total cancelled bookings");
        }
        return -1;
    }
    public int getTotalReturned() {
        String query = "SELECT COUNT(id) AS total FROM booking WHERE status = 'RETURNED';";
        try(ResultSet rs = db.executeQuery(query)) {
            if (rs.next()){
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Failed to get total returned bookings");
        }
        return -1;
    }
    public int getTotalCustomer() {
        String query = "SELECT COUNT(id) AS total FROM users WHERE is_admin = false;";
        try(ResultSet rs = db.executeQuery(query)) {
            if (rs.next()){
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Failed to get total number of our customers");
        }
        return -1;
    }
}
