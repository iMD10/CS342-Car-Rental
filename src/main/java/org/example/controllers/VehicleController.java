package org.example.controllers;
import org.example.classes.Vehicle;
import org.example.common.DatabaseHandler;
import org.example.common.ErrorHandler;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class VehicleController {// Add Vehicle, update Vehicle needs to be done
    private DatabaseHandler db;

    private List<Vehicle> getVehicles(ResultSet rs) throws  Exception {
        try {
            List<Vehicle> vehicles = new ArrayList<>();
            while (rs.next()) {
                vehicles.add(
                        new Vehicle(
                                rs.getInt("vehicle_id"),
                                rs.getInt("milage"),
                                rs.getInt("size"),
                                rs.getInt("model_year"),
                                rs.getString("serial_number"),
                                rs.getString("name"),
                                rs.getString("color"),
                                rs.getString("plate_number"),
                                rs.getDouble("price"),
                                rs.getString("company"),
                                rs.getString("type")
                        )
                );
            }
            return vehicles;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<Vehicle> getAllVehicles() {
        try {
            db = new DatabaseHandler();
            String query = "SELECT vehicle.id as vehicle_id, serial_number, plate_number, milage, color, name, model_year, price, company, size, type FROM vehicle NATURAL JOIN car_model";
            ResultSet rs = db.executeQuery(query);
            return getVehicles(rs);

        } catch (SQLException e) {
            ErrorHandler.handleException(e,e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public List<Vehicle> getAvailableVehicles(Timestamp start, Timestamp end) {
        try {
            db = new DatabaseHandler();
            String query = "SELECT vehicle.id AS vehicle_id, serial_number, plate_number, mileage, color, name, model_year,  price, company, size, type FROM vehicle JOIN car_model ON car_model_id = car_model.id LEFT JOIN booking ON vehicle.id = booking.vehicle_id  AND booking.status = 'active' AND (booking.start_date <= ? AND booking.end_date >= ?) WHERE booking.id IS NULL;";
            ResultSet rs = db.executeQuery(query, start, end);
            return getVehicles(rs);

        } catch (SQLException e) {
            ErrorHandler.handleException(e,e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Vehicle> getAvailableVehiclesByType(String type, Timestamp start, Timestamp end) {
        try {
            db = new DatabaseHandler();
            String query = "SELECT vehicle.id AS vehicle_id, serial_number, plate_number, mileage, color, name, model_year,  price, company, size, type FROM vehicle JOIN car_model ON car_model_id = car_model.id LEFT JOIN booking ON vehicle.id = booking.vehicle_id  AND booking.status = 'active' AND (booking.start_date <= ? AND booking.end_date >= ?) WHERE (booking.id IS NULL AND type = ?);";
            ResultSet rs = db.executeQuery(query, start, end, type);
            return getVehicles(rs);

        } catch (SQLException e) {
            ErrorHandler.handleException(e,e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
