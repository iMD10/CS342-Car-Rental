package org.example.view;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

//        Image icon = new ImageIcon(this.getClass().getResource("/carrental.png")).getImage();
//        this.setIconImage(icon);

        // Center panel for action buttons
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 50, 50));
        JButton manageVehicleButton = new JButton("Manage Vehicles");
        JButton manageBookingsButton = new JButton("Manage Bookings");
        JButton reportsButton = new JButton("Reports");
        centerPanel.add(manageVehicleButton);
        centerPanel.add(manageBookingsButton);
        centerPanel.add(reportsButton);

        // Bottom panel for action button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton ExitButton = new JButton("Exit");
        ExitButton.addActionListener((e -> System.exit(0)));
        bottomPanel.add(ExitButton);

        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);

        // Action listeners for navigation
        manageVehicleButton.addActionListener(e -> showFrame(new ManageVehicles(this)));
        manageBookingsButton.addActionListener(e -> showFrame(new ManageBookings(this)));
        reportsButton.addActionListener(e -> showFrame(new Reports(this)));

    }

    private void showFrame(JFrame frame) {
        this.setVisible(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}
