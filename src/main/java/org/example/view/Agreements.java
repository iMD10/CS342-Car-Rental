package org.example.view;

import org.example.classes.Agreement;
import org.example.classes.Booking;
import org.example.classes.User;
import org.example.classes.Vehicle;
import org.example.controllers.AgreementController;
import org.example.controllers.BookingController;
import org.example.controllers.VehicleController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Agreements extends JPanel {
    private AgreementController agreementController = new AgreementController();
    private BookingController bookingController = new BookingController();
    private VehicleController vehicleController = new VehicleController();

    private final MainFrame mainFrame;

    public Agreements(MainFrame mainFrame, User loggedUser) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Center panel for table
        String[] columnNames = {"ID", "Booking ID", "Car", "Start Date", "End Date" , "Booked At"};


        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        JTable agreementTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(agreementTable);

        // Bottom panel for action buttons
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton printButton = new JButton("View");
        JButton refreshButton = new JButton("Refresh");

        refreshButton.addActionListener(e -> updateAgreements(tableModel, loggedUser));

        actionButtonPanel.add(printButton);
        actionButtonPanel.add(refreshButton);


        // Add panels to the frame
        add(tableScrollPane, BorderLayout.CENTER);
        add(actionButtonPanel, BorderLayout.SOUTH);
        updateAgreements(tableModel, loggedUser);
        printButton.addActionListener(e -> {
            int selectedRow = agreementTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "No agreement selected. Please select an agreement to show.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int agreementId = (int) tableModel.getValueAt(selectedRow, 0);

                new SwingWorker<RentalAgreement, Void>() {
                    @Override
                    protected RentalAgreement doInBackground() throws Exception {
                        Agreement selectedAgreement = agreementController.getAgreementById(agreementId);
                        Booking booking = bookingController.getBookingByBookingId(selectedAgreement.getBookingId());
                        Vehicle vehicle = vehicleController.getVehicleByVehicleId(booking.getVehicleId());

                        return new RentalAgreement(
                                vehicle,
                                loggedUser,
                                booking.getStart_date().toLocalDateTime().toLocalDate(),
                                booking.getEnd_date().toLocalDateTime().toLocalDate(),
                                false, null, null
                        );
                    }

                    @Override
                    protected void done() {
                        try {
                            RentalAgreement ra = get();
                            // Display or handle the RentalAgreement instance as needed
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(Agreements.this, "Error loading agreement details: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }.execute();
            }
        });

    }

    public void updateAgreements(DefaultTableModel tableModel, User loggedUser) {
        // Clear the table while loading
        tableModel.setRowCount(0);

        new SwingWorker<List<Object[]>, Void>() {
            @Override
            protected List<Object[]> doInBackground() throws Exception {
                List<Agreement> agreementList = agreementController.getAgreementsByUserId(loggedUser.getId());
                List<Object[]> rows = new ArrayList<>();

                for (Agreement agreement : agreementList) {
                    Booking booking = bookingController.getBookingByBookingId(agreement.getBookingId());
                    Vehicle vehicle = vehicleController.getVehicleByVehicleId(booking.getVehicleId());

                    rows.add(new Object[]{
                            agreement.getId(),
                            booking.getId(),
                            vehicle.getCarModel().getName(),
                            booking.getStart_date().toLocalDateTime().toLocalDate(),
                            booking.getEnd_date().toLocalDateTime().toLocalDate(),
                            booking.getBookedAt()
                    });
                }
                return rows;
            }

            @Override
            protected void done() {
                try {
                    List<Object[]> rows = get();
                    SwingUtilities.invokeLater(() -> {
                        for (Object[] row : rows) {
                            tableModel.addRow(row);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(Agreements.this, "Error loading agreements: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

}
