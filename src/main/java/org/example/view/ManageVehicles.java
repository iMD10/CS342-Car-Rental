package org.example.view;

import org.example.classes.Vehicle;
import org.example.classes.CarModel;
import org.example.controllers.VehicleController;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManageVehicles extends JPanel {

    private final AdminDashboard dashboard;
    private final VehicleController vehicleController = new VehicleController();
    private JTable table;
    private Object[][] data;
    private final String[] columnNames = {"Image", "ID", "Name", "Type", "Price-per-day", "Color", "Year", "Serial Number"};

    public ManageVehicles(AdminDashboard dashboard) {
        this.dashboard = dashboard;
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Create initial table data
        updateTableData();
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(100); // Set row height to display images properly

        // Set custom renderer for the Image column
        table.getColumn("Image").setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof ImageIcon) {
                    JLabel label = new JLabel();
                    label.setIcon((ImageIcon) value);
                    return label;
                } else {
                    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
            }
        });

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
        new SwingWorker<Object[][], Void>() {
            @Override
            protected Object[][] doInBackground() throws Exception {
                List<Vehicle> allVehicles = vehicleController.getAllVehicles();
                if (allVehicles == null || allVehicles.isEmpty()) {
                    return new Object[0][columnNames.length]; // Return an empty table if no data
                }

                Object[][] tableData = new Object[allVehicles.size()][columnNames.length];
                for (int i = 0; i < allVehicles.size(); i++) {
                    Vehicle vehicle = allVehicles.get(i);
                    if (vehicle == null || vehicle.getCarModel() == null) {
                        continue; // Skip null or invalid vehicle data
                    }

                    String path = "src\\main\\java\\org\\example\\res\\" + vehicle.getCarModel().getName() + ".png";
                    ImageIcon imageIcon = null;
                    try {
                        imageIcon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(130, 90, Image.SCALE_SMOOTH));
                    } catch (Exception e) {
                        imageIcon = new ImageIcon(); // Use a default or empty icon if loading fails
                    }

                    tableData[i][0] = imageIcon;
                    tableData[i][1] = vehicle.getId();
                    tableData[i][2] = vehicle.getCarModel().getName();
                    tableData[i][3] = vehicle.getCarModel().getType();
                    tableData[i][4] = vehicle.getCarModel().getPrice();
                    tableData[i][5] = vehicle.getColor();
                    tableData[i][6] = vehicle.getCarModel().getModelYear();
                    tableData[i][7] = vehicle.getSerialNumber();
                }
                return tableData;
            }

            @Override
            protected void done() {
                try {
                    data = get(); // Retrieve the processed data from doInBackground()
                    if (data == null || table == null) {
                        JOptionPane.showMessageDialog(ManageVehicles.this, "No data available to display.", "Info", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    SwingUtilities.invokeLater(() -> {
                        table.setModel(new DefaultTableModel(data, columnNames){
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        });
                        table.getColumn("Image").setCellRenderer(new DefaultTableCellRenderer() {
                            @Override
                            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                                if (value instanceof ImageIcon) {
                                    JLabel label = new JLabel();
                                    label.setIcon((ImageIcon) value);
                                    return label;
                                } else {
                                    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                                }
                            }
                        });
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(ManageVehicles.this, "Error updating vehicle table: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }


    private void openAddVehicleDialog() {
        new SwingWorker<List<CarModel>, Void>() {
            @Override
            protected List<CarModel> doInBackground() throws Exception {
                return vehicleController.getCarModels();
            }

            @Override
            protected void done() {
                try {
                    List<CarModel> carModels = get();

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

                    saveButton.addActionListener(e -> {
                        int carModelIndex = carModelComboBox.getSelectedIndex();
                        String serialNumber = serialNumberField.getText();
                        String color = (String) colorComboBox.getSelectedItem();

                        if (carModelIndex == -1 || serialNumber.isEmpty() || color == null) {
                            JOptionPane.showMessageDialog(dialog, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            int carModelId = carModels.get(carModelIndex).getId();

                            new SwingWorker<Integer, Void>() {
                                @Override
                                protected Integer doInBackground() throws Exception {
                                    return vehicleController.addVehicle(carModelId, serialNumber, color);
                                }

                                @Override
                                protected void done() {
                                    try {
                                        int vehicleId = get();
                                        if (vehicleId != -1) {
                                            updateTableData();
                                            dialog.dispose();
                                        } else {
                                            JOptionPane.showMessageDialog(dialog, "Failed to add vehicle.", "Error", JOptionPane.ERROR_MESSAGE);
                                        }
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        JOptionPane.showMessageDialog(dialog, "Error adding vehicle: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }.execute();
                        }
                    });

                    cancelButton.addActionListener(e -> dialog.dispose());
                    dialog.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(ManageVehicles.this, "Error loading car models: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

}
