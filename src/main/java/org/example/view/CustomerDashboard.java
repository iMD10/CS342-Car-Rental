package org.example.view;

import javax.swing.*;
import java.awt.*;

public class CustomerDashboard extends JFrame {

    public CustomerDashboard() {
        setTitle("Customer Dashboard");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Center panel for action buttons with padding
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Add padding between buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JButton browseVehicleButton = new JButton("Browse Vehicles");
        gbc.gridy = 0;
        centerPanel.add(browseVehicleButton, gbc);

        JButton myBookingsButton = new JButton("My Bookings");
        gbc.gridy = 1;
        centerPanel.add(myBookingsButton, gbc);

        JButton viewInvoicesButton = new JButton("View Invoices");
        gbc.gridy = 2;
        centerPanel.add(viewInvoicesButton, gbc);

        JButton agreementsButton = new JButton("Agreements");
        gbc.gridy = 3;
        centerPanel.add(agreementsButton, gbc);

        // Bottom panel for logout button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton ExitButton = new JButton("Exit");
        ExitButton.addActionListener((e -> System.exit(0)));
        bottomPanel.add(ExitButton);

        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);

        // Action listeners for navigation
        browseVehicleButton.addActionListener(e -> showFrame(new BrowseVehicles(this)));
        myBookingsButton.addActionListener(e -> showFrame(new MyBookings(this)));
        viewInvoicesButton.addActionListener(e -> showFrame(new Invoices(this)));
        agreementsButton.addActionListener(e -> showFrame(new Agreements(this)));
    }

    private void showFrame(JFrame frame) {
        this.setVisible(false);
        frame.setVisible(true);
    }

public static void main(String[] args) {

    new CustomerDashboard();
    }
}
