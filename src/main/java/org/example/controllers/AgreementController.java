package org.example.controllers;

import org.example.classes.Agreement;
import org.example.classes.Invoice;
import org.example.common.DatabaseHandler;
import org.example.common.ErrorHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
public class AgreementController {
    private DatabaseHandler db;

    public Agreement createAgreement(int bookingId, Timestamp issuedAt ) {
        db = new DatabaseHandler();
        String terms = """
        CAR RENTAL AGREEMENT TERMS

        1. Rental Period:
        The vehicle must be returned on or before the agreed return date and time. Late returns may incur additional charges.

        2. Payment:
        The renter is responsible for rental fees, taxes, and any additional charges for damages or late returns.

        3. Vehicle Usage:
        The renter must use the vehicle responsibly, follow all traffic laws, and not allow unauthorized drivers.

        4. Damage Liability:
        The renter is liable for any damages not covered by insurance.

        5. Insurance:
        Additional insurance options are available and recommended.

        6. Prohibited Uses:
        The vehicle must not be used for racing, towing, or illegal activities.

        By booking a vehicle, you agree to these terms.
        """;
        String query = "INSERT INTO agreement (booking_id, terms, issued_at) VALUES (?,?,?)";

        try{
            int rs = db.executeUpdate(query,bookingId, terms, issuedAt);
            return new Agreement(rs,bookingId, terms, issuedAt);
        } catch (SQLException e) {
            ErrorHandler.handleException(e,e.getMessage());
        }
        finally {
            db.closeConnection();
        }
        return null;
    }
    public List<Agreement> getAllAgreements() {
        String query = "SELECT * FROM agreement";
        db = new DatabaseHandler();
        try (ResultSet rs = db.executeQuery(query)){
            List<Agreement> agreements = new ArrayList<>();
            while(rs.next()){
                agreements.add(new Agreement(rs.getInt("id"),rs.getInt("booking_id"),rs.getString("terms"),rs.getTimestamp("issued_at")));
            }
            return agreements;
        } catch (SQLException e) {
            ErrorHandler.handleException(e,"Couldn't get all agreements");
        }
        finally {
            db.closeConnection();
        }
        return null;
    }
    public List<Agreement> getAgreementsByUserId(int userId) {
        db = new DatabaseHandler();
        String query = "SELECT * FROM agreement JOIN booking on booking_id = booking.id WHERE user_id=?;";
        try (ResultSet rs = db.executeQuery(query, userId)){
            List<Agreement> agreements = new ArrayList<>();
            while(rs.next()){
                agreements.add(new Agreement(rs.getInt("id"),rs.getInt("booking_id"),rs.getString("terms"),rs.getTimestamp("issued_at")));
            }
            return agreements;
        } catch (SQLException e) {
            ErrorHandler.handleException(e,"Couldn't get customer agreements");
        }
        finally {
            db.closeConnection();
        }
        return null;
    }
    public Agreement getAgreementById(int id) {
        db = new DatabaseHandler();
        String query = "SELECT * FROM agreement WHERE id=?;";
        try (ResultSet rs = db.executeQuery(query, id)){

            if(rs.next()){
                return new Agreement(id,rs.getInt("booking_id"),rs.getString("terms"),rs.getTimestamp("issued_at"));
            }

        } catch (SQLException e) {
            ErrorHandler.handleException(e,"Couldn't get agreement");
        }
        finally {
            db.closeConnection();
        }
        return null;
    }

}
