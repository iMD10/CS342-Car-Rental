package org.example.view;

import org.example.classes.Invoice;
import org.example.classes.Booking;
import org.example.classes.User;
import org.example.classes.Vehicle;
import org.example.controllers.BookingController;
import org.example.controllers.InvoiceController;
import org.example.controllers.VehicleController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Invoices extends JPanel {
    private final InvoiceController invoiceController = new InvoiceController();
    private final VehicleController vehicleController = new VehicleController();
    private final BookingController bookingController = new BookingController();

    private final MainFrame mainFrame;

    public Invoices(MainFrame mainFrame, User loggedUser) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Table column names
        String[] columnNames = {"ID", "Car", "Start Date", "End Date", "Late Fees", "Total Price"};

        // Table model
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable invoicesTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(invoicesTable);

        // Bottom panel with buttons
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton refreshButton = new JButton("Refresh");
        JButton printButton = new JButton("Print");
        JButton backButton = new JButton("Back to Dashboard");

        // Refresh button action listener
        refreshButton.addActionListener(e -> updateInvoices(tableModel, loggedUser));

        actionButtonPanel.add(refreshButton);
        actionButtonPanel.add(printButton);
        actionButtonPanel.add(backButton);

        // Add panels to the layout
        add(tableScrollPane, BorderLayout.CENTER);
        add(actionButtonPanel, BorderLayout.SOUTH);

        // Initial load
        updateInvoices(tableModel, loggedUser);
    }

    private void updateInvoices(DefaultTableModel tableModel, User loggedUser) {
        tableModel.setRowCount(0); // Clear the table

        // Get invoices for the user
        System.out.println(loggedUser.getId());
        List<Invoice> invoices = invoiceController.getAllInvoicesByUserId(loggedUser.getId());
        // Cache bookings and vehicles to avoid redundant database queries
        Map<Integer, Booking> bookingCache = new HashMap<>();
        Map<Integer, Vehicle> vehicleCache = new HashMap<>();

        for (Invoice invoice : invoices) {
            // Get booking and cache it
            Booking booking = bookingCache.computeIfAbsent(invoice.getBooking_id(),
                    id -> bookingController.getBookingByBookingId(id));

            if (booking == null) continue; // Skip if booking is not found

            // Get vehicle and cache it
            Vehicle vehicle = vehicleCache.computeIfAbsent(booking.getVehicleId(),
                    id -> vehicleController.getVehicleByVehicleId(id));

            if (vehicle == null) continue; // Skip if vehicle is not found

            // Add row to the table
            tableModel.addRow(new Object[]{
                    invoice.getId(),
                    vehicle.getCarModel().getName(),
                    booking.getStart_date(),
                    booking.getEnd_date(),
                    invoice.getLate_fees(),
                    invoice.getTotal_price()
            });
        }
    }
}
