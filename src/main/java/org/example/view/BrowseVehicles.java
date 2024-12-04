package org.example.view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import org.example.classes.CarModel;
import org.example.classes.User;
import org.example.classes.Vehicle;
import org.example.controllers.VehicleController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BrowseVehicles extends JPanel {

    private final MainFrame mainFrame;
    private final VehicleController vehicleController;

    public BrowseVehicles(MainFrame mainFrame, User loggedUser) {
        this.mainFrame = mainFrame;
        this.vehicleController = new VehicleController(); // Initialize controller
        setLayout(new BorderLayout());

        // Top panel for filters
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel typeLabel = new JLabel("Type:");
        JComboBox<String> typeComboBox = new JComboBox<>(getVehicleTypes());

        JLabel fromLabel = new JLabel("From:");
        DatePickerSettings fromSettings = new DatePickerSettings();
        fromSettings.setAllowKeyboardEditing(false);
        DatePicker fromDatePicker = new DatePicker(fromSettings);
        fromDatePicker.setDate(LocalDate.now());

        JLabel toLabel = new JLabel("To:");
        DatePickerSettings toSettings = new DatePickerSettings();
        toSettings.setAllowKeyboardEditing(false);
        DatePicker toDatePicker = new DatePicker(toSettings);
        toDatePicker.setDate(LocalDate.now());

        JButton searchButton = new JButton("Search");

        topPanel.add(typeLabel);
        topPanel.add(typeComboBox);
        topPanel.add(fromLabel);
        topPanel.add(fromDatePicker);
        topPanel.add(toLabel);
        topPanel.add(toDatePicker);
        topPanel.add(searchButton);

        // Center panel for table
        String[] columnNames = {"id","Name", "Type", "Price-per-day", "Color", "Year"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 0 -> int.class;
                    default -> String.class;
                };
            }
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        JTable vehicleTable = new JTable(tableModel);
        vehicleTable.setRowHeight(60);
        JScrollPane tableScrollPane = new JScrollPane(vehicleTable);

        // Search button action listener
        searchButton.addActionListener(e -> {
            LocalDate fromDate = fromDatePicker.getDate();
            LocalDate toDate = toDatePicker.getDate();
            String selectedType = (String) typeComboBox.getSelectedItem();

            if (fromDate == null || toDate == null) {
                JOptionPane.showMessageDialog(this, "Please select both dates.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (fromDate.isAfter(toDate)) {
                JOptionPane.showMessageDialog(this, "'From' date cannot be after 'To' date.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (fromDate.isEqual(toDate)) {
                JOptionPane.showMessageDialog(this, "Can't book same day ,Please selcet two different Dates.", "Error", JOptionPane.ERROR_MESSAGE);

            } else {
                // Fetch available vehicles and update table
                Timestamp fromstamp = Timestamp.valueOf(fromDate.atStartOfDay());;
                Timestamp tostamp =Timestamp.valueOf(toDate.atStartOfDay());;
                List<Vehicle> vehicles = vehicleController.getAvailableVehiclesByType(selectedType, fromstamp, tostamp);
                updateTable(tableModel, vehicles);
            }
        });

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton bookButton = new JButton("Book Now");

        bookButton.addActionListener(e -> {
            int selectedRow = vehicleTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "No vehicle selected. Please select a vehicle to book.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Vehicle selectedVehicle =  vehicleController.getVehicleByVehicleId((int)tableModel.getValueAt(selectedRow, 0)); // Assuming Vehicle is stored in table model
                // Pass selectedVehicle to booking logic (e.g., open RentalAgreement)
                setVisible(false);
                RentalAgreement ra = new RentalAgreement(selectedVehicle, new User(1,"a","a","a","a",false) ,fromDatePicker.getDate(), toDatePicker.getDate());
            }
        });

        bottomPanel.add(bookButton);

        // Add panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);


    }

    private void updateTable(DefaultTableModel tableModel, List<Vehicle> vehicles) {
        tableModel.setRowCount(0); // Clear existing rows
        for (Vehicle vehicle : vehicles) {
//            ImageIcon icon = new ImageIcon(vehicle.getImagePath());
//            Image image = icon.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);
//            icon = new ImageIcon(image);

            tableModel.addRow(new Object[]{
                    vehicle.getId(),
                    vehicle.getCarModel().getName(),
                    vehicle.getCarModel().getType(),
                    vehicle.getCarModel().getPrice(),
                    vehicle.getColor(),
                    vehicle.getCarModel().getModelYear()// Unselected checkbox
            });

        }
    }

    private String[] getVehicleTypes() {
        // Fetch vehicle types dynamically from the controller
        List<CarModel> carModels= vehicleController.getCarModels();
        List<String> types = new ArrayList<>(carModels.size());
        for(CarModel cars :carModels) {
            types.add(cars.getType());
        }
        return types.toArray(new String[0]);
    }
}
