/*package org.example.view;
import com.formdev.flatlaf.*;
import javax.swing.*;
import java.awt.*;

public class CustomerDashboard extends JFrame {

    public CustomerDashboard() {
        setTitle("Customer Dashboard");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.setResizable(false);

        // Top panel for logo and title
        JPanel topPanel = new JPanel(new BorderLayout());

        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("res/sampleCar.png"); // Update with your logo path
        Image scaledImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Adjust width and height
        logoIcon = new ImageIcon(scaledImage);
        logoLabel.setIcon(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(logoLabel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Welcome To Blu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel, BorderLayout.SOUTH);

        // Center panel for action buttons with padding
        JPanel centerPanelContainer = new JPanel(new BorderLayout());
        centerPanelContainer.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 30, 20));
        JButton browseVehicleButton = new JButton("Browse Vehicles");
        browseVehicleButton.setBackground(new Color(0, 172, 237)); // Matching blue from the logo
        browseVehicleButton.setForeground(Color.WHITE);
        JButton myBookingsButton = new JButton("My Bookings");
        myBookingsButton.setBackground(new Color(0, 172, 237)); // Matching blue from the logo
        myBookingsButton.setForeground(Color.WHITE);
        JButton viewInvoicesButton = new JButton("View Invoices");
        viewInvoicesButton.setBackground(new Color(0, 172, 237)); // Matching blue from the logo
        viewInvoicesButton.setForeground(Color.WHITE);
        JButton agreementsButton = new JButton("Agreements");
        agreementsButton.setBackground(new Color(0, 172, 237)); // Matching blue from the logo
        agreementsButton.setForeground(Color.WHITE);
        centerPanel.add(browseVehicleButton);
        centerPanel.add(myBookingsButton);
        centerPanel.add(viewInvoicesButton);
        centerPanel.add(agreementsButton);

        centerPanelContainer.add(centerPanel, BorderLayout.CENTER);

        // Bottom panel for exit button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.LIGHT_GRAY);
        exitButton.setForeground(Color.BLACK);
        bottomPanel.add(exitButton);

        // Adding panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(centerPanelContainer, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);

        // Action listeners for navigation
        browseVehicleButton.addActionListener(e -> showFrame(new BrowseVehicles(this)));
        myBookingsButton.addActionListener(e -> showFrame(new MyBookings(this)));
        viewInvoicesButton.addActionListener(e -> showFrame(new Invoices(this)));
        agreementsButton.addActionListener(e -> showFrame(new Agreements(this)));
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void showFrame(JFrame frame) {
        this.setVisible(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        new CustomerDashboard();
    }
}
*/