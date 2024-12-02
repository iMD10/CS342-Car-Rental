package org.example.controllers;
import org.example.classes.Booking;
import org.example.common.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
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

        try{
            String query = "SELECT COUNT(*) AS count FROM booking WHERE vehicle_id = ? AND (start_date <= ? AND end_date >= ?)";
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
    public List<Booking> getAllBookingsByUserid(int userId){
        List<Booking> bookings = new ArrayList<>();
        String query = "select * from booking where user_id = ?";
        try {

        ResultSet resSet = DbHandler.executeQuery(query,userId);
        while (resSet != null && resSet.next()) {
            booking = new Booking(resSet.getInt("id"),
                    resSet.getInt("user_id"),
                    resSet.getInt("vehicle_id"),
                    resSet.getString("status"),
                    resSet.getTimestamp("booked_at"),
                    resSet.getTimestamp("returned_at"),
                    resSet.getTimestamp("start_date"),
                    resSet.getTimestamp("end_date")
                   );
            bookings.add(booking);
        }

      }
      catch (SQLException e) {
            e.printStackTrace();
      } catch (Exception ee) {
          ErrorHandler.showError(ee.getMessage()+"Error retrieving bookings for user ID: " + userId);
      }
        return bookings;
    }
    public void editBookingStatusToCanceld(int bookingId){

        String query = "UPDATE booking SET status = ? WHERE id = ?";
        try{
            DbHandler.executeUpdate(query,"CANCELD",bookingId);
        } catch (SQLException e) {
            ErrorHandler.handleException(e,"Error updating booking status to canceled");

        }

    }
    public void editBookingStatusToReturned(int bookingId){

        String query = "UPDATE booking SET status = ? WHERE id = ?";
        try{
            DbHandler.executeUpdate(query,"RETURNED",bookingId);
        } catch (SQLException e) {
            ErrorHandler.handleException(e,"Error updating booking status to Returned");
        }
    }
    public List<Booking> getAllBookings(){
        List<Booking> bookings = new ArrayList<>();
        String query = "select * from booking";
        try {

            ResultSet resSet = DbHandler.executeQuery(query);
            while (resSet != null && resSet.next()) {
                booking = new Booking(resSet.getInt("id"),
                        resSet.getInt("user_id"),
                        resSet.getInt("vehicle_id"),
                        resSet.getString("status"),
                        resSet.getTimestamp("booked_at"),
                        resSet.getTimestamp("returned_at"),
                        resSet.getTimestamp("start_date"),
                        resSet.getTimestamp("end_date")
                );
                bookings.add(booking);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ee) {
            ErrorHandler.showError(ee+"Error retrieving all bookings ");
        }
        return bookings;
    }

}
