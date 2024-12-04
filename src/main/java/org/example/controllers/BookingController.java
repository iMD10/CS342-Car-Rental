package org.example.controllers;
import org.example.classes.Booking;
import org.example.classes.Invoice;
import org.example.classes.Vehicle;
import org.example.common.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
public class BookingController {
    private DatabaseHandler DbHandler = new DatabaseHandler();;
    private Booking booking;
    private Invoice invoice;
    public Booking createBooking (int userId,int vehicleId,Timestamp start_date, Timestamp end_date ) {
        try {
            if (CarIsBusy(vehicleId, start_date, end_date)) {
                ErrorHandler.handleWarning("Car is Already Booked ");
                return null;
            }
            Timestamp now = new Timestamp(System.currentTimeMillis());

            // Calculating cost
            VehicleController vehicleController = new VehicleController();
            Vehicle vehicle = vehicleController.getVehicleByVehicleId(vehicleId);
            long differenceInMillis = end_date.getTime() - start_date.getTime();
            long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMillis);
            double cost = vehicle.getCarModel().getPrice() * differenceInDays;
            String query = "insert into booking (user_id, vehicle_id, booked_at, start_date, end_date, status, cost) VALUES (?, ?, ?, ?, ?, 'active', ?)";
            int id = DbHandler.executeUpdate(query, userId, vehicleId, now, start_date, end_date,cost);
            System.out.println(id);
            booking = new Booking(id, userId, vehicleId,"ACTIVE", now,null ,start_date, end_date, cost);
            return booking;
        }
        catch (SQLException e) {
            ErrorHandler.handleException(e,e.getMessage());
        }
        return null;
        }
    public Boolean CarIsBusy(int vehicleId, Timestamp start_date, Timestamp end_date) {
        String query = """
        SELECT COUNT(*) AS count 
        FROM booking 
        WHERE vehicle_id = ? 
          AND NOT (start_date > ? OR end_date < ?)
    """;

        try (ResultSet resSet = DbHandler.executeQuery(query, vehicleId, end_date, start_date)) {
            if (resSet.next() && resSet.getInt("count") > 0) {
                return true; // Car is busy
            }
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Error checking car availability");
        }
        return false; // Car is not busy
    }

    public List<Booking> getAllBookingsByUserid(int userId){
        List<Booking> bookings = new ArrayList<>();
        String query = "select * from booking where user_id = ?";
        try(ResultSet resSet = DbHandler.executeQuery(query,userId)) {


        while (resSet != null && resSet.next()) {
            bookings.add( new Booking(resSet.getInt("id"),
                    resSet.getInt("user_id"),
                    resSet.getInt("vehicle_id"),
                    resSet.getString("status"),
                    resSet.getTimestamp("booked_at"),
                    resSet.getTimestamp("returned_at"),
                    resSet.getTimestamp("start_date"),
                    resSet.getTimestamp("end_date"),
                    resSet.getDouble("cost")
                   ));

        }

      }
      catch (SQLException e) {
            ErrorHandler.handleException(e,e.getMessage());
      } catch (Exception ee) {
          ErrorHandler.showError(ee.getMessage()+"Error retrieving bookings for user ID: " + userId);
      }
        return bookings;
    }
    public void editBookingStatusToCanceled(int bookingId){

        String query = "UPDATE booking SET status = ? WHERE id = ?";
        try{
            DbHandler.executeUpdate(query,"CANCELD",bookingId);
        } catch (SQLException e) {
            ErrorHandler.handleException(e,"Error updating booking status to canceled");

        }

    }
    public void editBookingStatusToReturned(int bookingId){

        String query = "SELECT * from booking WHERE id = ?";
        try(ResultSet resSet = DbHandler.executeQuery(query,bookingId)){


            if(!resSet.next()){
                ErrorHandler.handleWarning("This Booking Doesn't exist");
                return;
            }
            Timestamp End_date =  resSet.getTimestamp("end_date");
            Timestamp now = new Timestamp(System.currentTimeMillis());
            long differenceInMillis = now.getTime() - End_date.getTime();

            long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMillis);
            double cost = resSet.getDouble("cost");
            double lateFees;
            if(differenceInMillis < 0 ){
                lateFees = 0;
            }
            else lateFees = cost*differenceInDays;
            query = "UPDATE booking SET status = ? WHERE id = ?";
            DbHandler.executeUpdate(query,"RETURNED",bookingId);
            query = "UPDATE booking SET returned_at = ? WHERE id = ?";
            DbHandler.executeUpdate(query,now,bookingId);

            InvoiceController invoiceController = new InvoiceController();
            invoice = invoiceController.createInvoice(bookingId,lateFees,cost+lateFees,now);


        } catch (SQLException e) {
            ErrorHandler.handleException(e,"Error updating booking status to Returned");
        }
    }
    public List<Booking> getAllBookings(){
        List<Booking> bookings = new ArrayList<>();
        String query = "select * from booking;";
        try( ResultSet resSet = DbHandler.executeQuery(query)) {
            while (resSet != null && resSet.next()) {
                bookings.add( new Booking(
                        resSet.getInt("id"),
                        resSet.getInt("user_id"),
                        resSet.getInt("vehicle_id"),
                        resSet.getString("status"),
                        resSet.getTimestamp("booked_at"),
                        resSet.getTimestamp("returned_at"),
                        resSet.getTimestamp("start_date"),
                        resSet.getTimestamp("end_date"),
                        resSet.getDouble("cost")
                ));

            }

        }
        catch (SQLException e) {
            ErrorHandler.handleException(e,e.getMessage());
        } catch (Exception ee) {
            ErrorHandler.showError(ee+"Error retrieving all bookings ");
        }
        return bookings;
    }

    public Booking getBookingByBookingId(int bookingId) {
        String query = "select * from booking where id = ?";
        try(ResultSet resSet = DbHandler.executeQuery(query,bookingId)) {
            if(resSet.next()){
               return new Booking(
                        resSet.getInt("id"),
                        resSet.getInt("user_id"),
                        resSet.getInt("vehicle_id"),
                        resSet.getString("status"),
                        resSet.getTimestamp("booked_at"),
                        resSet.getTimestamp("returned_at"),
                        resSet.getTimestamp("start_date"),
                        resSet.getTimestamp("end_date"),
                       resSet.getDouble("cost")
                );
            } else {
                throw new RuntimeException("Booking does not exist");
            }
        } catch (SQLException e) {
            ErrorHandler.handleException(e,e.getMessage());
        }
        return null;
    }

}
