package org.example.view;

import javax.swing.*;
import java.awt.*;

public class MyBookings extends JFrame {

    private final CustomerDashboard dashboard;

    public MyBookings(CustomerDashboard dashboard) {
        this.dashboard = dashboard;
        setTitle("My Bookings");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Center panel for table
        String[] columnNames = {"ID", "Car", "Start Date", "End Date", "Selected"};
        Object[][] data = {
                {"1", "ACCORD", "01/01/2024", "03/01/2024", false},
                {"2", "MaxCruize", "02/01/2024", "05/01/2024", false},
                {"3", "Fortuner", "01/02/2024", "07/02/2024", false}
        };

        JTable bookingsTable = new JTable(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) {
                    return Boolean.class;
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        JScrollPane tableScrollPane = new JScrollPane(bookingsTable);

        // Bottom panel for action buttons
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton cancelButton = new JButton("Cancel Booking");
        JButton backButton = new JButton("Back to Dashboard");

        cancelButton.addActionListener(e -> {
            int selectedRow = bookingsTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "No booking selected. Please select a booking to cancel.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel the selected booking?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Your booking has been cancelled", "Notification", JOptionPane.INFORMATION_MESSAGE);
                    this.setVisible(false);
                    dashboard.setVisible(true);
                }
            }
        });

        backButton.addActionListener(e -> {
            this.setVisible(false);
            dashboard.setVisible(true);
        });

        actionButtonPanel.add(cancelButton);
        actionButtonPanel.add(backButton);

        // Add panels to the frame
        add(tableScrollPane, BorderLayout.CENTER);
        add(actionButtonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}