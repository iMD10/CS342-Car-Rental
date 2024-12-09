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

    private JComboBox<String> typeComboBox;
    public BrowseVehicles(MainFrame mainFrame, User loggedUser) {
        this.mainFrame = mainFrame;
        this.vehicleController = new VehicleController();
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel typeLabel = new JLabel("Type:");


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

        typeComboBox = new JComboBox<>(getVehicleTypes());
        fromDatePicker.addDateChangeListener(event -> {
            LocalDate newFromDate = event.getNewDate();
            if (newFromDate != null) {
                if (!newFromDate.isBefore(toDatePicker.getDate())) {
                    toDatePicker.setDate(newFromDate.plusDays(1));
                }
                toDatePicker.getSettings().setDateRangeLimits(newFromDate.plusDays(1), null);
            }
        });

        toDatePicker.addDateChangeListener(event -> {
            LocalDate newToDate = event.getNewDate();
            if (newToDate != null && newToDate.isBefore(fromDatePicker.getDate())) {
                toDatePicker.setDate(fromDatePicker.getDate().plusDays(1));
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

                RentalAgreement ra = new RentalAgreement(selectedVehicle, loggedUser, fromDatePicker.getDate(), toDatePicker.getDate(), true, null, null );

            }
        });

        bottomPanel.add(bookButton);

        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);


        //updateTableAutomatically(typeComboBox, fromDatePicker, toDatePicker);
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

        new SwingWorker<List<Vehicle>, Void>() {
            @Override
            protected List<Vehicle> doInBackground() throws Exception {
                List<Vehicle> vehicles = new ArrayList<>() ;
                String selectedType1 = (String) typeComboBox.getSelectedItem();
                if(selectedType1 == null) {
                    vehicles =  vehicleController.getAvailableVehicles(fromstamp, tostamp);
                }
                else if (selectedType1.equals("All") ){
                    vehicles =  vehicleController.getAvailableVehicles(fromstamp, tostamp);
                } else {
                    vehicles = vehicleController.getAvailableVehiclesByType(selectedType1, fromstamp, tostamp);
                }
                if (vehicles == null) {
                    return new ArrayList<>();
                }
                return vehicles;
            }


            @Override
            protected void done() {
                try {
                    List<Vehicle> vehicles = get();

                    if (vehicles == null)
                        return;
                    updateTable(tableModel, vehicles);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(BrowseVehicles.this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void updateTableManually(JComboBox<String> typeComboBox, DatePicker fromDatePicker, DatePicker toDatePicker) {
        LocalDate fromDate = fromDatePicker.getDate();
        LocalDate toDate = toDatePicker.getDate();
        String selectedType = (String) typeComboBox.getSelectedItem();

        if (fromDate == null || toDate == null) {
            JOptionPane.showMessageDialog(this, "Please select both dates.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (fromDate.isAfter(toDate)) {
            JOptionPane.showMessageDialog(this, "'From' date cannot be after 'To' date.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (fromDate.isEqual(toDate)) {
            JOptionPane.showMessageDialog(this, "Can't book same day, please select two different dates.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Timestamp fromstamp = Timestamp.valueOf(fromDate.atStartOfDay());
        Timestamp tostamp = Timestamp.valueOf(toDate.atStartOfDay());

        new SwingWorker<List<Vehicle>, Void>() {
            @Override
            protected List<Vehicle> doInBackground() throws Exception {
                List<Vehicle> vehicles = new ArrayList<>() ;
                if (selectedType == null)
                    vehicles= vehicleController.getAvailableVehicles(fromstamp, tostamp);
               else if (selectedType.equals("All")){
                    vehicles= vehicleController.getAvailableVehicles(fromstamp, tostamp);
                }else {
                    vehicles = vehicleController.getAvailableVehiclesByType(selectedType, fromstamp, tostamp);
                }
               return vehicles;
            }

            @Override
            protected void done() {
                try {

                    List<Vehicle> vehicles = get();
                    if (vehicles == null) {
                        return;
                    }
                    updateTable(tableModel, vehicles);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(BrowseVehicles.this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }


    private void updateTable(DefaultTableModel tableModel, List<Vehicle> vehicles) {
        tableModel.setRowCount(0); // Clear existing rows

        if (vehicles == null) {
           return;
        }
        for (Vehicle vehicle : vehicles) {
            String path = "src/main/java/org/example/res/" + vehicle.getCarModel().getName() + ".png";
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(130, 90, Image.SCALE_SMOOTH));

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
        List<String> types = new ArrayList<>();

        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {

                List<String> strings = new ArrayList<>();
                strings = vehicleController.getCarTypes();
                if (strings == null) {
                    strings = new ArrayList<>() ;
                }
                return strings;
            }

            @Override
            protected void done() {
                try {
                    types.add("All");

                    List<String> typesFirst = get();
                    System.out.println("SwingWorker finished execution types.");
                    if (typesFirst == null) {
                        return;
                    }
                    types.addAll(get());

                    SwingUtilities.invokeLater(() -> typeComboBox.setModel(new DefaultComboBoxModel<>(types.toArray(new String[0]))));
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(BrowseVehicles.this, "Error loading vehicle types: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();

        return types.toArray(new String[0]);
    }

}
