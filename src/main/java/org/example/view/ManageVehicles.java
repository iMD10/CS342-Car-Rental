package org.example.view;

import org.example.classes.Vehicle;
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
        List<Vehicle> allVehicles = vehicleController.getAllVehicles();

        Object[][] data = new Object[allVehicles.size()][6];
        for (int i = 0; i < allVehicles.size(); i++) {
            Vehicle vehicle = allVehicles.get(i);
            data[i][0] = vehicle.getCarModel().getName();
            data[i][1] = vehicle.getCarModel().getType();
            data[i][2] = vehicle.getCarModel().getPrice();
            data[i][3] = vehicle.getColor();
            data[i][4] = vehicle.getCarModel().getModelYear();
            data[i][5] = false;
        }
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
                return false;
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
