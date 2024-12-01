package org.example.classes;
import java.sql.*;

public class Booking {
    private int id, userId, vehicleId;
    private String status;
    private Timestamp bookedAt,returnedAt, start_date, end_date;

    public Booking(int id, int userId, int vehicleId, String status, Timestamp bookedAt, Timestamp returnedAt, Timestamp start_date, Timestamp end_date) {
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.status = status;
        this.bookedAt = bookedAt;
        this.returnedAt = returnedAt;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(Timestamp bookedAt) {
        this.bookedAt = bookedAt;
    }

    public Timestamp getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(Timestamp returnedAt) {
        this.returnedAt = returnedAt;
    }

    public Timestamp getStart_date() {
        return start_date;
    }

    public void setStart_date(Timestamp start_date) {
        this.start_date = start_date;
    }

    public Timestamp getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Timestamp end_date) {
        this.end_date = end_date;
    }
}
