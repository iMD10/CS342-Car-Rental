package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageVehicles extends JFrame {

    private final AdminDashboard dashboard;

    public ManageVehicles(AdminDashboard dashboard) {
        this.dashboard = dashboard;
        setTitle("Manage Vehicles");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for date selection
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel fromLabel = new JLabel("From:");
        JTextField fromDateField = new JTextField("01/01/2024", 10);
        JLabel toLabel = new JLabel("To:");
        JTextField toDateField = new JTextField("04/01/2024", 10);
        topPanel.add(fromLabel);
        topPanel.add(fromDateField);
        topPanel.add(toLabel);
        topPanel.add(toDateField);

        // Center panel for table
        String[] columnNames = {"Name", "Type", "Price-per-day", "Color", "Year", "Selected"};
        Object[][] data = {
                {"ACCORD", "MIdSidan", "$500", "Black", "2021", false},
                {"MaxCruize", "SUV", "$200", "White", "2020", false},
                {"Fortuner", "SUV", "$220", "White", "2020", false}
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

        JTable vehicleTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(vehicleTable);

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> {
            this.setVisible(false);
            dashboard.setVisible(true);
        });
        bottomPanel.add(backButton);

        // Add panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
