package org.example.view;

import org.example.classes.Invoice;
import org.example.classes.Booking;
import org.example.classes.User;
import org.example.classes.Vehicle;
import org.example.common.ErrorHandler;
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

        // Refresh button action listener
        refreshButton.addActionListener(e -> updateInvoices(tableModel, loggedUser));

        actionButtonPanel.add(refreshButton);
        actionButtonPanel.add(printButton);
        printButton.addActionListener(e -> {
            JTable table = (JTable) tableScrollPane.getViewport().getView();
            int[] selectedRows = table.getSelectedRows();

            if (selectedRows.length > 1) {
                ErrorHandler.handleWarning("Can't select more than one row, please select one");
                return;
            } else if (selectedRows.length == 0) {
                ErrorHandler.handleWarning("No row selected, please select one");
                return;
            }

            int selectedInvoiceId = (Integer) table.getValueAt(selectedRows[0], 0);

            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    Invoice selectedInvoice = invoiceController.getInvoiceByInvoiceId(selectedInvoiceId);
                    Booking booking = bookingController.getBookingByBookingId(selectedInvoice.getBooking_id());
                    Vehicle vehicle = vehicleController.getVehicleByVehicleId(booking.getVehicleId());

                    PDFInvoiceGenerator.generateInvoice(
                            loggedUser.getName(),
                            vehicle.getCarModel().getName(),
                            booking.getStart_date().toLocalDateTime().toLocalDate(),
                            booking.getEnd_date().toLocalDateTime().toLocalDate(),
                            selectedInvoice.getTotal_price(),
                            selectedInvoice.getLate_fees()
                    );

                    return null;
                }

                @Override
                protected void done() {
                    try {
                        JOptionPane.showMessageDialog(Invoices.this, "Invoice generated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(Invoices.this, "Error generating invoice: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }.execute();
        });


        // Add panels to the layout
        add(tableScrollPane, BorderLayout.CENTER);
        add(actionButtonPanel, BorderLayout.SOUTH);

        // Initial load
        updateInvoices(tableModel, loggedUser);
    }

    private void updateInvoices(DefaultTableModel tableModel, User loggedUser) {
        tableModel.setRowCount(0); // Clear the table

        new SwingWorker<List<Object[]>, Void>() {
            @Override
            protected List<Object[]> doInBackground() throws Exception {
                // Fetch invoices
                List<Invoice> invoices = invoiceController.getAllInvoicesByUserId(loggedUser.getId());

                // Cache bookings and vehicles to avoid redundant database queries
                Map<Integer, Booking> bookingCache = new HashMap<>();
                Map<Integer, Vehicle> vehicleCache = new HashMap<>();
                List<Object[]> rows = new java.util.ArrayList<>();

                for (Invoice invoice : invoices) {
                    // Get booking and cache it
                    Booking booking = bookingCache.computeIfAbsent(invoice.getBooking_id(),
                            id -> bookingController.getBookingByBookingId(id));

                    // Get vehicle and cache it
                    Vehicle vehicle = vehicleCache.computeIfAbsent(booking.getVehicleId(),
                            id -> vehicleController.getVehicleByVehicleId(id));

                    // Prepare row data
                    rows.add(new Object[]{
                            invoice.getId(),
                            vehicle.getCarModel().getName(),
                            booking.getStart_date().toLocalDateTime().toLocalDate(),
                            booking.getEnd_date().toLocalDateTime().toLocalDate(),
                            invoice.getLate_fees(),
                            invoice.getTotal_price()
                    });
                }
                return rows;
            }

            @Override
            protected void done() {
                try {
                    List<Object[]> rows = get();
                    SwingUtilities.invokeLater(() -> {
                        for (Object[] row : rows) {
                            tableModel.addRow(row);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(Invoices.this, "Error loading invoices: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

}
