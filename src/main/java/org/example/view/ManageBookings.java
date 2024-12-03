package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageBookings extends JPanel  {

   private final AdminDashboard dashboard;


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
        String[] columnNames = {"ID", "Customer", "Car", "Start Date", "End Date", "Selected", "Status"};
        Object[][] data = {
                {"1", "ahmed", "ACCORD", "01/01/2024", "03/01/2024", false, "Canceled"},
                {"2", "saleh", "MaxCruize", "02/01/2024", "05/01/2024", false, "Returned"},
                {"2", "saleh", "MaxCruize", "02/01/2024", "05/01/2024", false, "Active"}
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 5) {
                    return Boolean.class;
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };

        JTable bookingTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(bookingTable);

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
    }
}
