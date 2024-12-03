package org.example.view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.time.LocalDate;

public class BrowseVehicles extends JPanel {

    private String[] carsCategory = {"Camry", "Accord", "Sonata", "Yaris", "Accent", "Azera"};
    private final MainFrame mainFrame;

    public BrowseVehicles(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
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

        // Center panel for table with images
        String[] columnNames = {"Photo", "Name", "Type", "Price-per-day", "Color", "Year", "Selected"};
        Object[][] data = {
                {new ImageIcon("C:\\Users\\Eyad9.DESKTOP-BCQI8E7\\OneDrive\\Desktop\\Accord.png"), "ACCORD", "MidSedan", "$500", "Black", "2021", false},
                {new ImageIcon("C:\\Users\\Eyad9.DESKTOP-BCQI8E7\\OneDrive\\Desktop\\MaxCruize.png"), "MaxCruize", "SUV", "$200", "White", "2020", false},
                {new ImageIcon("C:\\Users\\Eyad9.DESKTOP-BCQI8E7\\OneDrive\\Desktop\\Fortuner.jpg"), "Fortuner", "SUV", "$220", "White", "2020", false}
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return ImageIcon.class;
                }
                if (columnIndex == 6) {
                    return Boolean.class;
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };

        JTable vehicleTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                if (component instanceof JLabel && column == 0) {
                    JLabel label = (JLabel) component;
                    ImageIcon icon = (ImageIcon) getValueAt(row, column);
                    if (icon != null) {
                        Image image = icon.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);
                        label.setIcon(new ImageIcon(image));
                    }
                }
                return component;
            }
        };

        vehicleTable.setRowHeight(60); // Adjust row height to fit images

        JScrollPane tableScrollPane = new JScrollPane(vehicleTable);

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton bookButton = new JButton("Book Now");
        bookButton.addActionListener(e -> {
            int selectedRow = vehicleTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "No vehicle selected. Please select a vehicle to book.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int response = JOptionPane.showConfirmDialog(null, "Do you want to proceed?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    // Booking logic here
                }
            }
        });

        bottomPanel.add(bookButton);

        // Add panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
