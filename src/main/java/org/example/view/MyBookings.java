package org.example.view;

import org.example.classes.Booking;
import org.example.classes.User;
import org.example.common.ErrorHandler;
import org.example.common.TableCreator;
import org.example.controllers.BookingController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import org.example.controllers.BookingController;
import org.example.controllers.VehicleController;

public class MyBookings extends JPanel {

    private final MainFrame mainFrame;
    private  BookingController bookingController= new BookingController();
    private  VehicleController vehicleController= new VehicleController();
    public MyBookings(MainFrame mainFrame, User loggedUser) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Center panel for table
        String[] columnNames = {"ID", "Car","Booked At", "Start Date", "End Date","Status", "Cost"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable bookingTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(bookingTable);




        // Bottom panel for action buttons
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton cancelButton = new JButton("Cancel Booking");
        JButton refreshButton = new JButton("Refresh");

        refreshButton.addActionListener(e -> {
            updateBooking(tableModel, loggedUser);

        });

        cancelButton.addActionListener(e -> {
            JTable table = (JTable) tableScrollPane.getViewport().getView();
            int[] selectedRows = table.getSelectedRows();
            if(selectedRows.length > 1){
                ErrorHandler.handleWarning("Can't select more than one row, please select one");
                return;
            }
            else if(selectedRows.length == 0){
                ErrorHandler.handleWarning("No row selected, please select one");
                return;
            } else if (table.getValueAt(selectedRows[0], 4).equals("CANCELD") ) {
                ErrorHandler.handleWarning("this booking is already cancelled");
                return;
            } else {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel the selected booking?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    // Cancellation logic here
                    bookingController.editBookingStatusToCanceled((Integer) table.getValueAt(selectedRows[0],0));
                    table.getModel().setValueAt("CANCELD", selectedRows[0], 4);
                }
            }
        });

        actionButtonPanel.add(cancelButton);
        actionButtonPanel.add(refreshButton);

        updateBooking(tableModel, loggedUser);
        // Add panels to the frame
        add(tableScrollPane, BorderLayout.CENTER);
        add(actionButtonPanel, BorderLayout.SOUTH);
    }

    private void updateBooking(DefaultTableModel tableModel, User loggedUser) {
        tableModel.setRowCount(0);
        List<Booking> allBookings = bookingController.getAllBookingsByUserid(loggedUser.getId());

        for(Booking booking : allBookings){
            tableModel.addRow(new Object[]{
                    booking.getId(),
                    vehicleController.getVehicleByVehicleId(booking.getVehicleId()).getCarModel().getName(),
                    booking.getBookedAt(),
                    booking.getStart_date().toLocalDateTime().toLocalDate(),
                    booking.getEnd_date().toLocalDateTime().toLocalDate(),
                    booking.getStatus(),
                    booking.getCost()
            });
        }
    }
}
