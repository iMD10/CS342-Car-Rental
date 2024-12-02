package org.example.view;

import javax.swing.*;
import java.awt.*;

public class ConfirmBooking extends JFrame {

    public ConfirmBooking() {
        setTitle("Booking Details");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Center panel for booking details
        JPanel detailsPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel vehicleNameLabel = new JLabel("Vehicle:");
        JLabel vehicleNameValue = new JLabel("Accord");

        JLabel startDateLabel = new JLabel("Start Date:");
        JLabel startDateValue = new JLabel("01/01/2024");

        JLabel endDateLabel = new JLabel("End Date:");
        JLabel endDateValue = new JLabel("03/01/2024");

        JLabel totalPriceLabel = new JLabel("Total Price:");
        JLabel totalPriceValue = new JLabel("$500");

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
        actionButtonPanel.add(confirmButton);
        actionButtonPanel.add(cancelButton);

        // Add panels to the frame
        add(detailsPanel, BorderLayout.CENTER);
        add(actionButtonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new ConfirmBooking();
    }
}
