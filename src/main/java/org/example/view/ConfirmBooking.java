package org.example.view;

import javax.swing.*;
import java.awt.*;

public class ConfirmBooking extends JFrame {

    private final MainFrame mainFrame;

    public ConfirmBooking(MainFrame mainFrame, ImageIcon vehicleImage, String vehicleName) {
        this.mainFrame = mainFrame;
        setTitle("Rental Agreement");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Center panel for booking details
        JPanel detailsPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel vehicleImageLabel = new JLabel();
        vehicleImageLabel.setIcon(vehicleImage);

        JLabel vehicleNameLabel = new JLabel("Vehicle:");
        JLabel vehicleNameValue = new JLabel(vehicleName);

        JLabel startDateLabel = new JLabel("Start Date:");
        JLabel startDateValue = new JLabel("01/01/2024");

        JLabel endDateLabel = new JLabel("End Date:");
        JLabel endDateValue = new JLabel("03/01/2024");

        JLabel totalPriceLabel = new JLabel("Total Price:");
        JLabel totalPriceValue = new JLabel("$500");

        detailsPanel.add(vehicleImageLabel);
        detailsPanel.add(new JLabel("")); // Placeholder for grid alignment
        detailsPanel.add(vehicleNameLabel);
        detailsPanel.add(vehicleNameValue);
        detailsPanel.add(startDateLabel);
        detailsPanel.add(startDateValue);
        detailsPanel.add(endDateLabel);
        detailsPanel.add(endDateValue);
        detailsPanel.add(totalPriceLabel);
        detailsPanel.add(totalPriceValue);

        // Bottom panel for action buttons
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton confirmButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");

        confirmButton.addActionListener(e -> {
            // Switch to Invoices panel upon confirmation
            this.setVisible(false);
            mainFrame.switchPanel("Invoices");
            JOptionPane.showMessageDialog(mainFrame, "Booking confirmed!");
        });

        cancelButton.addActionListener(e -> {
            this.setVisible(false);
        });

        actionButtonPanel.add(confirmButton);
        actionButtonPanel.add(cancelButton);

        // Add panels to the frame
        add(detailsPanel, BorderLayout.CENTER);
        add(actionButtonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
