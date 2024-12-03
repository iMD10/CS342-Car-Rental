package org.example.view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class BrowseVehicles extends JFrame {
    private String carsCategory[] = {"Camry", "Accord", "Sonata", "Yaris", "Accent", "Azera"};
    private final CustomerDashboard dashboard;

    public BrowseVehicles(CustomerDashboard dashboard) {
        this.dashboard = dashboard;
        setTitle("Browse Vehicles");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for date selection
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel nameLabel = new JLabel("Name:");
        JComboBox<String> nameField = new JComboBox<>(carsCategory);

        JLabel fromLabel = new JLabel("From:");
        DatePickerSettings fromSettings = new DatePickerSettings();
        fromSettings.setAllowKeyboardEditing(false);
        DatePicker fromDatePicker = new DatePicker(fromSettings);
        fromDatePicker.setDate(LocalDate.now()); // Set default date to today
        fromDatePicker.getSettings().setDateRangeLimits(LocalDate.now(), null); // Prevent past dates

        JLabel toLabel = new JLabel("To:");
        DatePickerSettings toSettings = new DatePickerSettings();
        toSettings.setAllowKeyboardEditing(false);
        DatePicker toDatePicker = new DatePicker(toSettings);
        toDatePicker.setDate(LocalDate.now()); // Set default date to today
        toDatePicker.getSettings().setDateRangeLimits(LocalDate.now(), null); // Prevent past dates

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            LocalDate fromDate = fromDatePicker.getDate();
            LocalDate toDate = toDatePicker.getDate();

            if (fromDate == null || toDate == null) {
                JOptionPane.showMessageDialog(this, "Please select both dates.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (fromDate.isAfter(toDate)) {
                JOptionPane.showMessageDialog(this, "'From' date cannot be after 'To' date.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Perform search logic here
            }
        });

        topPanel.add(nameLabel);
        topPanel.add(nameField);
        topPanel.add(fromLabel);
        topPanel.add(fromDatePicker);
        topPanel.add(toLabel);
        topPanel.add(toDatePicker);
        topPanel.add(searchButton);

        // Center panel for table
        String[] columnNames = {"Name", "Type", "Price-per-day", "Color", "Year", "Selected"};
        Object[][] data = {
                {"ACCORD", "MidSedan", "$500", "Black", "2021", false},
                {"MaxCruize", "SUV", "$200", "White", "2020", false},
                {"Fortuner", "SUV", "$220", "White", "2020", false}
        };

        JTable vehicleTable = new JTable(data, columnNames) {
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

        vehicleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Ensure single row selection

        JScrollPane tableScrollPane = new JScrollPane(vehicleTable);

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton backButton = new JButton("Back to Dashboard");
        JButton bookButton = new JButton("Book Now");
        bookButton.addActionListener(e -> {
            int selectedRow = vehicleTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "No vehicle selected. Please select a vehicle to book.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int response = JOptionPane.showConfirmDialog(null, "Do you want to proceed?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    this.setVisible(false);
                    Invoices pells = new Invoices(dashboard);
                }
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