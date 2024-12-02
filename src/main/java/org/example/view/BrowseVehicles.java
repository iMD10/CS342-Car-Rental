package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BrowseVehicles extends JFrame {
    private String carsCategory[]= {"Camry", "Accord", "Sonata","Yaris","Accent","Azera"};
    private final CustomerDashboard dashboard;

    public BrowseVehicles(CustomerDashboard dashboard) {
        this.dashboard = dashboard;
        setTitle("Manage Vehicles");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for date selection
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel nameLabel = new JLabel("Name:");

//        JTextField nameField = new JTextField("Accord", 10);
        JComboBox nameField = new JComboBox(carsCategory);

        JLabel fromLabel = new JLabel("From:");
        JTextField fromDateField = new JTextField("01/01/2024", 10);
        JLabel toLabel = new JLabel("To:");
        JTextField toDateField = new JTextField("04/01/2024", 10);
        JButton searchButton = new JButton("Search");
        topPanel.add(nameLabel);
        topPanel.add(nameField);
        topPanel.add(fromLabel);
        topPanel.add(fromDateField);
        topPanel.add(toLabel);
        topPanel.add(toDateField);
        topPanel.add(searchButton);

        // Center panel for table
        String[] columnNames = {"Name", "Type", "Price-per-day", "Color", "Year", "Selected"};
        Object[][] data = {
                {"ACCORD", "MidSedan", "$500", "Black", "2021", false},
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
        JButton bookButton = new JButton("Book Now");
        bookButton.addActionListener(e -> {

            int response = JOptionPane.showConfirmDialog(null, "Do you want to proceed?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                this.setVisible(false);
                Invoices pells = new Invoices(dashboard);
            }

        });
        backButton.addActionListener(e -> {
            this.setVisible(false);
            dashboard.setVisible(true);
        });

        bottomPanel.add(backButton);
        bottomPanel.add(bookButton);
        // Add panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }


}
