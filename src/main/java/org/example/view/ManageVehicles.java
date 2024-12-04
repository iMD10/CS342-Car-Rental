package org.example.view;

import org.example.classes.Vehicle;
import org.example.classes.CarModel;
import org.example.controllers.VehicleController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManageVehicles extends JPanel {

    private final AdminDashboard dashboard;
    private final VehicleController vehicleController = new VehicleController();
    private JTable table;
    private Object[][] data;
    private final String[] columnNames = {"ID", "Name", "Type", "Price-per-day", "Color", "Year", "Serial Number"};

    public ManageVehicles(AdminDashboard dashboard) {
        this.dashboard = dashboard;
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Create initial table data
        updateTableData();
        table = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Add Vehicle");


        bottomPanel.add(addButton);
        

        // Add panels to the frame
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openAddVehicleDialog();
            }
        });


    }

    private void updateTableData() {
        List<Vehicle> allVehicles = vehicleController.getAllVehicles();
        data = new Object[allVehicles.size()][columnNames.length];
        for (int i = 0; i < allVehicles.size(); i++) {
            Vehicle vehicle = allVehicles.get(i);
            data[i][0] = vehicle.getId();
            data[i][1] = vehicle.getCarModel().getName();
            data[i][2] = vehicle.getCarModel().getType();
            data[i][3] = vehicle.getCarModel().getPrice();
            data[i][4] = vehicle.getColor();
            data[i][5] = vehicle.getCarModel().getModelYear();
            data[i][6] = vehicle.getSerialNumber();
        }
        if (table != null) {
            table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        }
    }

    private void openAddVehicleDialog() {
        List<CarModel> carModels = vehicleController.getCarModels();

        JDialog dialog = new JDialog((Frame) null, "Add New Vehicle", true);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(null);

        JLabel carModelLabel = new JLabel("Car Model:");
        JComboBox<String> carModelComboBox = new JComboBox<>(carModels.stream().map(CarModel::getDetails).toArray(String[]::new));
        JLabel serialNumberLabel = new JLabel("Serial Number:");
        JTextField serialNumberField = new JTextField();
        JLabel colorLabel = new JLabel("Color:");
        JComboBox<String> colorComboBox = new JComboBox<>(new String[]{"White", "Black", "Grey", "Red"});
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        dialog.add(carModelLabel);
        dialog.add(carModelComboBox);
        dialog.add(serialNumberLabel);
        dialog.add(serialNumberField);
        dialog.add(colorLabel);
        dialog.add(colorComboBox);
        dialog.add(saveButton);
        dialog.add(cancelButton);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int carModelIndex = carModelComboBox.getSelectedIndex();
                String serialNumber = serialNumberField.getText();
                String color = (String) colorComboBox.getSelectedItem();

                if (carModelIndex == -1 || serialNumber.isEmpty() || color == null) {
                    JOptionPane.showMessageDialog(dialog, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    int carModelId = carModels.get(carModelIndex).getId();
                    int vehicleId = vehicleController.addVehicle(carModelId, serialNumber, color);
                        updateTableData();
                    if (vehicleId != -1) {
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Failed to add vehicle.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }
}
