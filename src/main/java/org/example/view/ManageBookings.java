package org.example.view;

import org.example.classes.Booking;
import org.example.common.ErrorHandler;
import org.example.controllers.BookingController;
import org.example.common.TableCreator;
import org.example.controllers.UserController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
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
        String[] columnNames = {"ID", "Customer ID","Customer Name", "Car ID", "Start Date", "End Date", "Status"};



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
        JTable table = (JTable) tableScrollPane.getViewport().getView();
        int[] selectedRows = table.getSelectedRows();
        if(selectedRows.length > 1){
            ErrorHandler.handleWarning("Can't select more than one row, please select one");
            return;
        }
        else if(selectedRows.length == 0){
            ErrorHandler.handleWarning("No row selected, please select one");
            return;
        } else if (table.getValueAt(selectedRows[0], 6).equals("CANCELD") ) {
            ErrorHandler.handleWarning("this booking is already cancelled");
            return;
        }
        bookingController.editBookingStatusToCanceled((Integer) table.getValueAt(selectedRows[0],0));
        table.getModel().setValueAt("CANCELD", selectedRows[0], 6);

        });
        markReturnedButton.addActionListener(e->{
            JTable table = (JTable) tableScrollPane.getViewport().getView();
            int[] selectedRows = table.getSelectedRows();
            if(selectedRows.length > 1){
                ErrorHandler.handleWarning("Can't select more than one row, please select one");
                return;
            }
            else if(selectedRows.length == 0){
                ErrorHandler.handleWarning("No row selected, please select one");
                return;
            } else if (table.getValueAt(selectedRows[0], 6).equals("RETURNED") ) {
                ErrorHandler.handleWarning("this booking is already RETURNED");
                return;
            }
            bookingController.editBookingStatusToReturned((Integer) table.getValueAt(selectedRows[0],0));
            table.getModel().setValueAt("RETURNED", selectedRows[0], 6);
        });

        searchButton.addActionListener(e->{
            if (!(searchField.getText().isEmpty())) {
                try {
                    updateBookings(tableModel, Integer.parseInt(searchField.getText()));
                } catch (Exception ex) {
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

    public void updateBookings(DefaultTableModel tableModel, int bookingId){
        tableModel.setRowCount(0);
        List<Booking> allBookings = new ArrayList<>();
        if (bookingId == -1) {
             allBookings = bookingController.getAllBookings();
        } else {
             allBookings.add(bookingController.getBookingByBookingId(bookingId));
        }
        for(Booking booking : allBookings){
            tableModel.addRow(new Object[]{
                    booking.getId(),
                    booking.getUserId(),
                    userController.getUserById(booking.getUserId()).getName(),
                    booking.getVehicleId(),
                    booking.getStart_date(),
                    booking.getEnd_date(),
                    booking.getStatus(),
            });
        }
    }
}
