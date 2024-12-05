package org.example.view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import org.example.classes.CarModel;
import org.example.classes.User;
import org.example.classes.Vehicle;
import org.example.controllers.VehicleController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BrowseVehicles extends JPanel {

    private final MainFrame mainFrame;
    private final VehicleController vehicleController;
    private DefaultTableModel tableModel;

    public BrowseVehicles(MainFrame mainFrame, User loggedUser) {
        this.mainFrame = mainFrame;
        this.vehicleController = new VehicleController();
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel typeLabel = new JLabel("Type:");
        JComboBox<String> typeComboBox = new JComboBox<>(getVehicleTypes());

        JLabel fromLabel = new JLabel("From:");
        DatePickerSettings fromSettings = new DatePickerSettings();
        fromSettings.setAllowKeyboardEditing(false);
        DatePicker fromDatePicker = new DatePicker(fromSettings);
        fromDatePicker.setDate(LocalDate.now());
        fromDatePicker.getSettings().setDateRangeLimits(LocalDate.now(), null);

        JLabel toLabel = new JLabel("To:");
        DatePickerSettings toSettings = new DatePickerSettings();
        toSettings.setAllowKeyboardEditing(false);
        DatePicker toDatePicker = new DatePicker(toSettings);
        toDatePicker.setDate(LocalDate.now().plusDays(1));
        toDatePicker.getSettings().setDateRangeLimits(LocalDate.now().plusDays(1), null);

        fromDatePicker.addDateChangeListener(event -> {
            if (event.getNewDate() != null && event.getNewDate().isBefore(LocalDate.now())) {
                fromDatePicker.setDate(LocalDate.now());
            } else {
                LocalDate fromDate = fromDatePicker.getDate();
                if (fromDate != null) {
                    toDatePicker.getSettings().setDateRangeLimits(fromDate.plusDays(1), null);
                } else {
                    toDatePicker.getSettings().setDateRangeLimits(LocalDate.now().plusDays(1), null);
                }
                updateTableAutomatically(typeComboBox, fromDatePicker, toDatePicker);
            }
        });

        toDatePicker.addDateChangeListener(event -> {
            if (event.getNewDate() != null && !event.getNewDate().isAfter(LocalDate.now())) {
                toDatePicker.setDate(LocalDate.now().plusDays(1));
            } else {
                updateTableAutomatically(typeComboBox, fromDatePicker, toDatePicker);
            }
        });

        typeComboBox.addActionListener(e -> updateTableAutomatically(typeComboBox, fromDatePicker, toDatePicker));

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> updateTableManually(typeComboBox, fromDatePicker, toDatePicker));

        topPanel.add(typeLabel);
        topPanel.add(typeComboBox);
        topPanel.add(fromLabel);
        topPanel.add(fromDatePicker);
        topPanel.add(toLabel);
        topPanel.add(toDatePicker);
        topPanel.add(searchButton);

        String[] columnNames = {"Image", "ID", "Name", "Type", "Price-per-day", "Color", "Year"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 0 -> ImageIcon.class;
                    case 1 -> int.class;
                    default -> String.class;
                };
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable vehicleTable = new JTable(tableModel);
        vehicleTable.setRowHeight(100);
        vehicleTable.getColumn("Image").setCellRenderer(new DefaultTableCellRenderer() {
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
        JScrollPane tableScrollPane = new JScrollPane(vehicleTable);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton bookButton = new JButton("Book Now");

        bookButton.addActionListener(e -> {
            int selectedRow = vehicleTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "No vehicle selected. Please select a vehicle to book.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Vehicle selectedVehicle = vehicleController.getVehicleByVehicleId((int) tableModel.getValueAt(selectedRow, 1));
                setVisible(false);
                RentalAgreement ra = new RentalAgreement(selectedVehicle, loggedUser, fromDatePicker.getDate(), toDatePicker.getDate(), true);
            }
        });

        bottomPanel.add(bookButton);

        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        updateTableAutomatically(typeComboBox, fromDatePicker, toDatePicker);
    }

    private void updateTableAutomatically(JComboBox<String> typeComboBox, DatePicker fromDatePicker, DatePicker toDatePicker) {
        LocalDate fromDate = fromDatePicker.getDate();
        LocalDate toDate = toDatePicker.getDate();
        String selectedType = (String) typeComboBox.getSelectedItem();

        if (fromDate == null || toDate == null || fromDate.isAfter(toDate)) {
            return;
        }

        Timestamp fromstamp = Timestamp.valueOf(fromDate.atStartOfDay());
        Timestamp tostamp = Timestamp.valueOf(toDate.atStartOfDay());
        List<Vehicle> vehicles = vehicleController.getAvailableVehiclesByType(selectedType, fromstamp, tostamp);
        updateTable(tableModel, vehicles);
    }

    private void updateTableManually(JComboBox<String> typeComboBox, DatePicker fromDatePicker, DatePicker toDatePicker) {
        LocalDate fromDate = fromDatePicker.getDate();
        LocalDate toDate = toDatePicker.getDate();
        String selectedType = (String) typeComboBox.getSelectedItem();

        if (fromDate == null || toDate == null) {
            JOptionPane.showMessageDialog(this, "Please select both dates.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (fromDate.isAfter(toDate)) {
            JOptionPane.showMessageDialog(this, "'From' date cannot be after 'To' date.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (fromDate.isEqual(toDate)) {
            JOptionPane.showMessageDialog(this, "Can't book same day, please select two different dates.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Timestamp fromstamp = Timestamp.valueOf(fromDate.atStartOfDay());
            Timestamp tostamp = Timestamp.valueOf(toDate.atStartOfDay());
            List<Vehicle> vehicles = vehicleController.getAvailableVehiclesByType(selectedType, fromstamp, tostamp);
            updateTable(tableModel, vehicles);
        }
    }

    private void updateTable(DefaultTableModel tableModel, List<Vehicle> vehicles) {
        tableModel.setRowCount(0);
        for (Vehicle vehicle : vehicles) {
            String path = "res/" + vehicle.getCarModel().getName() + ".png";
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));

            tableModel.addRow(new Object[]{
                    imageIcon,
                    vehicle.getId(),
                    vehicle.getCarModel().getName(),
                    vehicle.getCarModel().getType(),
                    vehicle.getCarModel().getPrice(),
                    vehicle.getColor(),
                    vehicle.getCarModel().getModelYear()
            });
        }
    }

    private String[] getVehicleTypes() {
        List<CarModel> carModels = vehicleController.getCarModels();
        List<String> types = new ArrayList<>(carModels.size());
        for (CarModel cars : carModels) {
            types.add(cars.getType());
        }
        return types.toArray(new String[0]);
    }
}
