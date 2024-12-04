package org.example.view;

import org.example.classes.User;
import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    private JPanel contentPanel;

    public AdminDashboard(User loggedUser) {
        setTitle("Admin Dashboard");
        setSize(870, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top bar for navigation
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topBar.setBackground(new Color(0, 172, 237));

        // Add logo to the top bar
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("res\\R.png");
        Image scaledImage = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledImage);
        logoLabel.setIcon(logoIcon);

        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);
        logoPanel.setPreferredSize(new Dimension(85, 85));
        logoPanel.add(logoLabel, BorderLayout.CENTER);
        topBar.add(logoPanel);

        Dimension buttonSize = new Dimension(130, 85);
        JButton vehiclesButton = new JButton("Manage Vehicles");
        JButton bookingsButton = new JButton("Manage Bookings");
        JButton reportsButton = new JButton("Reports");
        JButton customersButton = new JButton("Customers");
        JButton logoutButton = new JButton("Logout");

        JButton[] buttons = {vehiclesButton, bookingsButton, reportsButton, customersButton };
        for (JButton button : buttons) {
            button.setBackground(new Color(0, 172, 237));
            button.setForeground(Color.WHITE);
            button.setPreferredSize(buttonSize);
            topBar.add(button);
        }

        logoutButton.setBackground(new Color(161, 1, 1));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setPreferredSize(buttonSize);
        topBar.add(logoutButton);


        add(topBar, BorderLayout.NORTH);

        // Content panel
        contentPanel = new JPanel(new CardLayout());
        contentPanel.add(new ManageVehicles(this), "Manage Vehicles");
        contentPanel.add(new ManageBookings(this), "Manage Bookings");
        contentPanel.add(new ReportsPanel(), "Reports");

        add(contentPanel, BorderLayout.CENTER);

        // Action listeners for navigation
        vehiclesButton.addActionListener(e -> switchPanel("Manage Vehicles"));
        bookingsButton.addActionListener(e -> switchPanel("Manage Bookings"));
        reportsButton.addActionListener(e -> switchPanel("Reports"));

        logoutButton.addActionListener(e ->{
            dispose();
            Login login = new Login();
        });



        setVisible(true);
    }

    public void switchPanel(String panelName) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, panelName);
    }
}
