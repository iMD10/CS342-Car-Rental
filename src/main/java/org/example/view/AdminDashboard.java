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

        // Top panel for logo and title
        JPanel topPanel = new JPanel(new BorderLayout());

        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\Eyad9.DESKTOP-BCQI8E7\\OneDrive\\Desktop\\R.png"); // Update with your logo path
        Image scaledImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Adjust width and height
        logoIcon = new ImageIcon(scaledImage);
        logoLabel.setIcon(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(logoLabel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Admin dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel, BorderLayout.SOUTH);

        // Center panel for action buttons with padding
        JPanel centerPanelContainer = new JPanel(new BorderLayout());
        centerPanelContainer.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        JButton manageVehicleButton = new JButton("Manage vehicles");
        manageVehicleButton.setBackground(new Color(0, 172, 237)); // Matching blue from the logo
        manageVehicleButton.setForeground(Color.WHITE);
        JButton manageBookingsButton = new JButton("Manage Bookings");
        manageBookingsButton.setBackground(new Color(0, 172, 237)); // Matching blue from the logo
        manageBookingsButton.setForeground(Color.WHITE);
        JButton reportsButton = new JButton("Reports");
        reportsButton.setBackground(new Color(0, 172, 237)); // Matching blue from the logo
        reportsButton.setForeground(Color.WHITE);
        centerPanel.add(manageVehicleButton);
        centerPanel.add(manageBookingsButton);
        centerPanel.add(reportsButton);

        centerPanelContainer.add(centerPanel, BorderLayout.CENTER);

        // Bottom panel for Exit button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton ExitButton = new JButton("Exit");
        ExitButton.setBackground(Color.LIGHT_GRAY);
        ExitButton.setForeground(Color.BLACK);
        bottomPanel.add(ExitButton);

        // Adding panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(centerPanelContainer, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);

        // Action listeners for navigation
        manageVehicleButton.addActionListener(e -> showFrame(new ManageVehicles(this)));
        manageBookingsButton.addActionListener(e -> showFrame(new ManageBookings(this)));
        reportsButton.addActionListener(e -> showFrame(new Reports(this)));
        ExitButton.addActionListener(e -> System.exit(0));
    }

    private void showFrame(JFrame frame) {
        this.setVisible(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}
