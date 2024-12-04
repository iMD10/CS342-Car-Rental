package org.example.view;

import org.example.classes.Booking;
import org.example.common.ErrorHandler;
import org.example.controllers.BookingController;
import org.example.common.TableCreator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageBookings extends JPanel  {

   private final AdminDashboard dashboard;
   private final BookingController bookingController= new BookingController();

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
        String[] columnNames = {"ID", "Customer ID", "Car ID", "Start Date", "End Date", "Status", "Selected"};
        List<Booking> allBookings = bookingController.getAllBookings();

        Object[][] data = new Object[allBookings.size()][columnNames.length];
        for(int i = 0 ; i < allBookings.size();i++){

            data[i][0] = allBookings.get(i).getId();
            data[i][1] = allBookings.get(i).getUserId();
            data[i][2] = allBookings.get(i).getVehicleId();
            data[i][3] = allBookings.get(i).getStart_date();
            data[i][4] = allBookings.get(i).getEnd_date();
            data[i][5] = allBookings.get(i).getStatus();
            data[i][6] = false;
        }



        JScrollPane tableScrollPane = TableCreator.createTablePanel(columnNames, data, new boolean[]{false, false, false, false, false, false, true});


        // Bottom panel for action buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton cancelButton = new JButton("Cancel Booking");
        JButton markReturnedButton = new JButton("Mark as Returned");
        JButton backButton = new JButton("Back to Dashboard");
        bottomPanel.add(cancelButton);
        bottomPanel.add(markReturnedButton);
        bottomPanel.add(backButton);

        // Add panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);

        // Action listener for "Back to Dashboard" button
        backButton.addActionListener(e -> {
            this.setVisible(false);
            dashboard.setVisible(true);
        });
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
        } else if (table.getValueAt(selectedRows[0], 5).equals("CANCELD") ) {
            ErrorHandler.handleWarning("this booking is already cancelled");
            return;
        }
            bookingController.editBookingStatusToCanceled((Integer) table.getValueAt(selectedRows[0],0));
        table.getModel().setValueAt("CANCELD", selectedRows[0], 5);

        });



    }
}
