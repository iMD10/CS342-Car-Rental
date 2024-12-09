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
            db = new DatabaseHandler();
            String query = "SELECT vehicle.id as vehicle_id, car_model_id, serial_number, color, name, model_year, price, company, type FROM vehicle JOIN car_model on car_model_id = car_model.id";
        try (ResultSet rs = db.executeQuery(query)){

            return getVehicles(rs);

        } catch (SQLException e) {
            ErrorHandler.handleException(e,e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            db.closeConnection();
        }
        return null;
    }
    public List<Vehicle> getAvailableVehicles(Timestamp start, Timestamp end) {
            String query = "SELECT car_model_id, vehicle.id AS vehicle_id, car_model_id, serial_number, color, name, model_year, price, company, type FROM vehicle JOIN car_model ON car_model_id = car_model.id LEFT JOIN booking ON vehicle.id = booking.vehicle_id  AND booking.status = 'active' AND (booking.start_date <= ? AND booking.end_date >= ?) WHERE (booking.id IS NULL);";
            db = new DatabaseHandler();
        try(ResultSet rs = db.executeQuery(query, end, start)) {

            return getVehicles(rs);

        } catch (SQLException e) {
            ErrorHandler.handleException(e,e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            ErrorHandler.handleException(e,e.getMessage());
            return new ArrayList<>();
        }finally {
            db.closeConnection();
        }
    }

    public List<Vehicle> getAvailableVehiclesByType(String type, Timestamp start, Timestamp end) {
            String query = "SELECT car_model_id, vehicle.id AS vehicle_id, car_model_id, serial_number, color, name, model_year, price, company, type FROM vehicle JOIN car_model ON car_model_id = car_model.id LEFT JOIN booking ON vehicle.id = booking.vehicle_id  AND booking.status = 'active' AND (booking.start_date <= ? AND booking.end_date >= ?) WHERE (booking.id IS NULL AND type = ?);";
            db = new DatabaseHandler();
        try(ResultSet rs = db.executeQuery(query, end, start, type)) {


            return getVehicles(rs);

        } catch (SQLException e) {
            ErrorHandler.handleException(e,e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            ErrorHandler.handleException(e,e.getMessage());
            return new ArrayList<>();
        }finally {
            db.closeConnection();
        }
    }

    public int addVehicle(int car_modelId, String serialNumber, String color ) {
        try {
            db = new DatabaseHandler();
            String query = "INSERT INTO vehicle (car_model_id, serial_number, color) VALUES (?, ?, ?);";
            return db.executeUpdate(query, car_modelId, serialNumber, color);

        } catch (SQLException e) {
            ErrorHandler.handleException(e,e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            db.closeConnection();
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
            return new ArrayList<>();
        } catch (Exception e) {
            ErrorHandler.handleException(e,e.getMessage());
            return new ArrayList<>();
        }finally {
            db.closeConnection();
        }
    }

    public List<String> getCarTypes() {
        db = new DatabaseHandler();
        String query = "SELECT distinct type FROM car_model;";
        try (ResultSet rs = db.executeQuery(query)){

            List<String> carTypes = new ArrayList<>();
            while (rs.next()) {
                carTypes.add(rs.getString("type"));
            }
            return carTypes;

        } catch (SQLException e) {
            ErrorHandler.handleException(e,e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            ErrorHandler.handleException(e,e.getMessage());
            return new ArrayList<>();
        }finally {
            db.closeConnection();
        }
    }

    public Vehicle getVehicleByVehicleId(int vehicleId) {
        db = new DatabaseHandler();
        String query = " SELECT vehicle.id as vehicle_id, car_model_id, model_year, serial_number, name, color, company, type, price FROM vehicle JOIN car_model on car_model_id = car_model.id WHERE vehicle.id = ?;";
        try (ResultSet rs = db.executeQuery(query, vehicleId)) {
            if (rs.next()) {
                return new Vehicle(
                        rs.getInt("vehicle_id"),
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
        }finally {
            db.closeConnection();
        }
        return null;
    }



}
