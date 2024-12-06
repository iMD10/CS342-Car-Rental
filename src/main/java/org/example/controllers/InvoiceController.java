package org.example.controllers;

import org.example.classes.Invoice;
import org.example.common.DatabaseHandler;
import org.example.common.ErrorHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
public class InvoiceController {
    private DatabaseHandler dbHandler;
    private Invoice invoice;

    public Invoice createInvoice(int bookingId, double lateFees, double totalPrice, Timestamp issuedAt) {
        String query = "INSERT INTO invoice (booking_id, late_fees, total_price, issued_at) VALUES (?, ?, ?, ?)";
        dbHandler = new DatabaseHandler();
        try {
            int inv =  dbHandler.executeUpdate(query, bookingId, lateFees, totalPrice, issuedAt);
            invoice = new Invoice(inv,bookingId, lateFees, totalPrice, issuedAt);
            return invoice;
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Error creating a new invoice");
            return null;
        } finally {
            dbHandler.closeConnection();
        }
    }
    public List<Invoice> getAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        dbHandler = new DatabaseHandler();
        String query = "SELECT * FROM invoice";
        try (ResultSet resSet = dbHandler.executeQuery(query)){
            while (resSet != null && resSet.next()) {
                invoice = new Invoice(
                        resSet.getInt("id"),
                        resSet.getInt("booking_id"),
                        resSet.getDouble("late_fees"),
                        resSet.getDouble("total_price"),
                        resSet.getTimestamp("issued_at")
                );
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Error retrieving all invoices");
        } catch (Exception ee) {
            ErrorHandler.showError(ee.getMessage() + " Error retrieving all invoices");
        }finally {
            dbHandler.closeConnection();
        }
        return invoices;
    }

    public List<Invoice> getAllInvoicesByUserId(int userId) {
        dbHandler = new DatabaseHandler();
        String query = "SELECT invoice.id as invoice_id, booking_id, late_fees, total_price, issued_at FROM invoice JOIN booking on booking_id = booking.id Where user_id = ?;";
        try(ResultSet rs = dbHandler.executeQuery(query,userId)) {
            List<Invoice> invoices = new ArrayList<>();
            while(rs.next()) {
                invoices.add( new Invoice(
                        rs.getInt("invoice_id"),
                        rs.getInt("booking_id"),
                        rs.getDouble("late_fees"),
                        rs.getDouble("total_price"),
                        rs.getTimestamp("issued_at")
                ));
            }
            return invoices;
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Error retrieving all invoices");
        }finally {
            dbHandler.closeConnection();
        }
        return null;
    }
    public Invoice getInvoiceByBookingId(int bookingId) {
        dbHandler = new DatabaseHandler();
        String query = "SELECT * FROM invoice where booking_id = ?;";
        try (ResultSet resSet = dbHandler.executeQuery(query, bookingId)) {

            if (resSet != null && resSet.next()) {
                return new Invoice(
                        resSet.getInt("id"),
                        resSet.getInt("booking_id"),
                        resSet.getDouble("late_fees"),
                        resSet.getDouble("total_price"),
                        resSet.getTimestamp("issued_at"));
            }

        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Error retrieving all invoices");
        } catch (Exception ee) {
            ErrorHandler.showError(ee.getMessage() + " Error retrieving all invoices");
        }finally {
            dbHandler.closeConnection();
        }
        return null;
    }
    public Invoice getInvoiceByInvoiceId(int invoiceId) {
        dbHandler = new DatabaseHandler();
        String query = "SELECT * FROM invoice where id = ?;";
        try (ResultSet resSet = dbHandler.executeQuery(query, invoiceId)) {

            if (resSet != null && resSet.next()) {
                return new Invoice(
                        resSet.getInt("id"),
                        resSet.getInt("booking_id"),
                        resSet.getDouble("late_fees"),
                        resSet.getDouble("total_price"),
                        resSet.getTimestamp("issued_at"));
            }

        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Error retrieving all invoices");
        } catch (Exception ee) {
            ErrorHandler.showError(ee.getMessage() + " Error retrieving all invoices");
        }finally {
            dbHandler.closeConnection();
        }
        return null;
    }


}
