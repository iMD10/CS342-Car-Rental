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
    private DatabaseHandler dbHandler = new DatabaseHandler();
    private Invoice invoice;

    public Invoice createInvoice(int bookingId, double lateFees, double totalPrice, Timestamp issuedAt) {
        String query = "INSERT INTO invoice (booking_id, late_fees, total_price, issued_at) VALUES (?, ?, ?, ?)";
        try {
            int inv =  dbHandler.executeUpdate(query, bookingId, lateFees, totalPrice, issuedAt);
            invoice = new Invoice(inv,bookingId, lateFees, totalPrice, issuedAt);
            return invoice;
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Error creating a new invoice");
            return null;
        }
    }
    public List<Invoice> getAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        String query = "SELECT * FROM invoice";
        try {
            ResultSet resSet = dbHandler.executeQuery(query);
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
        }
        return invoices;
    }


}
