package org.example.view;

import org.example.classes.Booking;
import org.example.classes.User;
import org.example.common.ErrorHandler;
import org.example.common.TableCreator;
import org.example.controllers.BookingController;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import org.example.controllers.BookingController;
import org.example.controllers.VehicleController;

public class MyBookings extends JPanel {

    private final MainFrame mainFrame;
    private final BookingController bookingController= new BookingController();
    private final VehicleController vehicleController= new VehicleController();
    public MyBookings(MainFrame mainFrame, User loggedUser) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Center panel for table
        String[] columnNames = {"ID", "Car", "Start Date", "End Date","Status"};
        List<Booking> allBookings = bookingController.getAllBookingsByUserid(loggedUser.getId());
        Object[][] data = new Object[allBookings.size()][columnNames.length];
        for(int i = 0 ; i < allBookings.size();i++){

            data[i][0] = allBookings.get(i).getId();
            data[i][1] = vehicleController.getVehicleByVehicleId(allBookings.get(i).getVehicleId()).getCarModel().getName();
            data[i][2] = allBookings.get(i).getStart_date();
            data[i][3] = allBookings.get(i).getEnd_date();
            data[i][4] = allBookings.get(i).getStatus();

        }

        JScrollPane tableScrollPane = TableCreator.createTablePanel(columnNames, data, new boolean[]{false, false, false, false, false, false});

        // Bottom panel for action buttons
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton cancelButton = new JButton("Cancel Booking");
        JButton refreshButton = new JButton("Refresh");

        refreshButton.addActionListener(e -> {
            this.revalidate();
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

        // Add panels to the frame
        add(tableScrollPane, BorderLayout.CENTER);
        add(actionButtonPanel, BorderLayout.SOUTH);
    }
}
