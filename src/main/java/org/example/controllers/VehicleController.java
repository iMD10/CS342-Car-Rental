package org.example.controllers;
import org.example.classes.CarModel;
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
                                rs.getInt("car_model_id"),
                                rs.getInt("model_year"),
                                rs.getString("serial_number"),
                                rs.getString("name"),
                                rs.getString("color"),
                                rs.getString("company"),
                                rs.getString("type"),
                                rs.getDouble("price")
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
            String query = "SELECT vehicle.id as vehicle_id, serial_number, color, name, model_year, price, company, type FROM vehicle NATURAL JOIN car_model";
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
            String query = "SELECT vehicle.id AS vehicle_id, serial_number, color, name, model_year,  price, company, type FROM vehicle JOIN car_model ON car_model_id = car_model.id LEFT JOIN booking ON vehicle.id = booking.vehicle_id  AND booking.status = 'active' AND (booking.start_date <= ? AND booking.end_date >= ?) WHERE booking.id IS NULL;";
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
            String query = "SELECT vehicle.id AS vehicle_id, serial_number, color, name, model_year, price, company, type FROM vehicle JOIN car_model ON car_model_id = car_model.id LEFT JOIN booking ON vehicle.id = booking.vehicle_id  AND booking.status = 'active' AND (booking.start_date <= ? AND booking.end_date >= ?) WHERE (booking.id IS NULL AND type = ?);";
            ResultSet rs = db.executeQuery(query, start, end, type);
            return getVehicles(rs);

        } catch (SQLException e) {
            ErrorHandler.handleException(e,e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int addVehicle(int car_modelId, String serialNumber, String color ) {
        try {
            db = new DatabaseHandler();
            String query = "INSERT INTO vehicle (car_model_id, serial_number, color) VALUES (?, ?, ?);";
            int rs = db.executeUpdate(query, car_modelId, serialNumber, color);
            return rs;

        } catch (SQLException e) {
            ErrorHandler.handleException(e,e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public List<CarModel> getCarModels() {
            db = new DatabaseHandler();
            String query = "SELECT * FROM car_model;";
        try (ResultSet rs = db.executeQuery(query)){

            List<CarModel> carModels = new ArrayList<>();
            while (rs.next()) {
                carModels.add(
                        new CarModel(
                                rs.getInt("id"),
                                rs.getInt("model_year"),
                                rs.getString("name"),
                                rs.getString("company"),
                                rs.getString("type"),
                                rs.getDouble("price")
                        )
                );
            }
            return carModels;

        } catch (SQLException e) {
            ErrorHandler.handleException(e,e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Vehicle getVehicleByVehicleId(int vehicleId) {
        db = new DatabaseHandler();
        String query = "SELECT * FROM vehicle JOIN car_model on car_model_id = car_model.id WHERE vehicle.id = ?;";
        try (ResultSet rs = db.executeQuery(query, vehicleId)) {
            if (rs.next()) {
                return new Vehicle(
                        rs.getInt("vehicle.id"),
                        rs.getInt("car_model_id"),
                        rs.getInt("model_year"),
                        rs.getString("serial_number"),
                        rs.getString("name"),
                        rs.getString("color"),
                        rs.getString("company"),
                        rs.getString("type"),
                        rs.getDouble("price")

                );
            }
        } catch (SQLException e) {
            ErrorHandler.handleException(e,e.getMessage());
        }
        return null;
    }



}
