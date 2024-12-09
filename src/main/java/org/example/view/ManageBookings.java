package org.example.view;

import org.example.classes.Booking;
import org.example.classes.Invoice;
import org.example.classes.User;
import org.example.common.ErrorHandler;
import org.example.controllers.BookingController;
import org.example.common.TableCreator;
import org.example.controllers.InvoiceController;
import org.example.controllers.UserController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ManageBookings extends JPanel  {

   private final AdminDashboard dashboard;
   private final BookingController bookingController= new BookingController();
   private final UserController userController= new UserController();
    public ManageBookings(AdminDashboard dashboard) {
        this.dashboard=dashboard;
//        setTitle("Manage Bookings");
        setSize(700, 500);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for search by ID
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel searchLabel = new JLabel("Search by ID:");
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(searchButton);

        // Center panel for table
        String[] columnNames = {"ID", "Customer Name", "Car ID", "Start Date", "End Date", "Status", "Cost", "Late Fees", "Total Price"};



        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };



        JTable bookingsTable = new JTable(tableModel);
        bookingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(bookingsTable);



        // Bottom panel for action buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton cancelButton = new JButton("Cancel Booking");
        JButton markReturnedButton = new JButton("Mark as Returned");
        JButton refreshButton = new JButton("Refresh");
        bottomPanel.add(cancelButton);
        bottomPanel.add(markReturnedButton);
        bottomPanel.add(refreshButton);


        // Add panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);


        cancelButton.addActionListener(e -> {
            int selectedRow = bookingsTable.getSelectedRow();
            if (selectedRow == -1) {
                ErrorHandler.handleWarning("No row selected, please select one");
                return;
            }

            String status = (String) bookingsTable.getValueAt(selectedRow, 5);
            if ("CANCELLED".equals(status)) {
                ErrorHandler.handleWarning("This booking is already cancelled");
                return;
            }

            int bookingId = (int) bookingsTable.getValueAt(selectedRow, 0);

            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    bookingController.editBookingStatusToCanceled(bookingId);
                    return null;
                }

                @Override
                protected void done() {
                    try {
                        bookingsTable.setValueAt("CANCELLED", selectedRow, 5);
                        ErrorHandler.handleInfo("Booking cancelled successfully.");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        ErrorHandler.handleException(ex,"Error cancelling booking: " + ex.getMessage());
                    }
                }
            }.execute();
        });

        markReturnedButton.addActionListener(e -> {
            int selectedRow = bookingsTable.getSelectedRow();
            if (selectedRow == -1) {
                ErrorHandler.handleWarning("No row selected, please select one");
                return;
            }

            String status = (String) bookingsTable.getValueAt(selectedRow, 5);
            if ("RETURNED".equals(status)) {
                ErrorHandler.handleWarning("This booking is already marked as returned");
                return;
            }

            int bookingId = (int) bookingsTable.getValueAt(selectedRow, 0);

            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    bookingController.editBookingStatusToReturned(bookingId);
                    return null;
                }

                @Override
                protected void done() {
                    try {
                        bookingsTable.setValueAt("RETURNED", selectedRow, 5);
                        ErrorHandler.handleInfo("Booking marked as returned successfully.");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        ErrorHandler.handleException(ex,"Error marking booking as returned: " + ex.getMessage());
                    }
                }
            }.execute();
        });


        searchButton.addActionListener(e->{
            if (!(searchField.getText().isEmpty())) {
                try {

                    updateBookings(tableModel, Integer.parseInt(searchField.getText()));
                } catch (NumberFormatException ex) {
                    ErrorHandler.handleWarning("Enter ID number");
                }
            } else {
                updateBookings(tableModel, -1);
            }
        });

        refreshButton.addActionListener(e->{
            updateBookings(tableModel, -1);
        });

        updateBookings(tableModel, -1);

    }

    public void updateBookings(DefaultTableModel tableModel, int bookingId) {
        tableModel.setRowCount(0); // Clear the table

        new SwingWorker<List<Object[]>, Void>() {
            @Override
            protected List<Object[]> doInBackground() throws Exception {
                // Retrieve bookings based on the bookingId
                List<Booking> allBookings = (bookingId == -1)
                        ? bookingController.getAllBookings()
                        : Collections.singletonList(bookingController.getBookingByBookingId(bookingId));

                // Fetch user details for all bookings in a single operation (if possible)
                Map<Integer, User> userMap = new HashMap<>();
                for (Booking booking : allBookings) {
                    if (!userMap.containsKey(booking.getUserId())) {
                        userMap.put(booking.getUserId(), userController.getUserById(booking.getUserId()));
                    }
                }

                // Prepare data for the table
                List<Object[]> rows = new ArrayList<>();
                for (Booking booking : allBookings) {
                    User user = userMap.get(booking.getUserId());
                    InvoiceController invoiceController = new InvoiceController();
                    //Invoice invoice = null;
                    Invoice invoice = invoiceController.getInvoiceByBookingId(booking.getId());
                    rows.add(new Object[] {
                            booking.getId(),
                            user != null ? user.getName() : "Unknown",
                            booking.getVehicleId(),
                            booking.getStart_date().toLocalDateTime().toLocalDate(),
                            booking.getEnd_date().toLocalDateTime().toLocalDate(),
                            booking.getStatus(),
                            booking.getCost(),
                            invoice != null ? invoice.getLate_fees() : "-",
                            invoice != null ? invoice.getTotal_price() : booking.getCost()
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
                    ErrorHandler.handleException(e,"Error updating bookings: " + e.getMessage());
                }
            }
        }.execute();
    }


}

