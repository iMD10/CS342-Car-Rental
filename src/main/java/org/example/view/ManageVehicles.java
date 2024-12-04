package org.example.view;

import org.example.classes.Vehicle;
import org.example.common.TableCreator;
import org.example.controllers.VehicleController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageVehicles extends JPanel  {

   private final AdminDashboard dashboard;
   private final VehicleController vehicleController = new VehicleController();

    public ManageVehicles(AdminDashboard dashboard) {

        this.dashboard=dashboard;
//         setTitle("Manage Vehicles");
        setSize(600, 400);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Center panel for table
        String[] columnNames = {"Name", "Type", "Price-per-day", "Color", "Year"};
        List<Vehicle> allVehicles = vehicleController.getAllVehicles();

        Object[][] data = new Object[allVehicles.size()][columnNames.length];
        for (int i = 0; i < allVehicles.size(); i++) {
            Vehicle vehicle = allVehicles.get(i);
            data[i][0] = vehicle.getCarModel().getName();
            data[i][1] = vehicle.getCarModel().getType();
            data[i][2] = vehicle.getCarModel().getPrice();
            data[i][3] = vehicle.getColor();
            data[i][4] = vehicle.getCarModel().getModelYear();

        }
        JScrollPane tableScrollPane = TableCreator.createTablePanel(columnNames, data, new boolean[]{false, false, false, false, false});

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> {
            this.setVisible(false);
            dashboard.setVisible(true);
        });
        bottomPanel.add(backButton);

        // Add panels to the frame

        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
