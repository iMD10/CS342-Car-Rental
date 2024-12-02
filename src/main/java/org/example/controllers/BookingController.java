package org.example.controllers;
import org.example.classes.Booking;
import org.example.common.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

public class BookingController {
    private DatabaseHandler DbHandler = new DatabaseHandler();;
    private Booking booking;

    public Booking createBooking (int userId,int vehicleId,Timestamp start_date, Timestamp end_date ) {
    try {
        if (CarIsBusy(vehicleId, start_date, end_date))
            return null;
        String query = "insert into booking (user_id, vehicle_id, booked_at, start_date, end_date, status) VALUES (?, ?, ?, ?, ?, 'active')";
        DbHandler.executeUpdate(query, userId, vehicleId, System.currentTimeMillis(), start_date, end_date);

        return booking;
    }
    catch (SQLException e) {
        return null;
    }
    }
    public Boolean CarIsBusy(int vehicleId,Timestamp start_date, Timestamp end_date){
        String query = "SELECT COUNT(*) AS count FROM Bookings \" +\n" +
                "                   \"WHERE vehicle_id = ? \" +\n" +
                "                   \"AND (start_date <= ? AND end_date >= ?)";
        try{
            ResultSet resSet = DbHandler.executeQuery(query,vehicleId,end_date,start_date);
            if(resSet.next()){
                ErrorHandler.showError("This Booking is already exist");
                return true;
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
