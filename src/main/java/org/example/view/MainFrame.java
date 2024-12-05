package org.example.view;

import org.example.classes.User;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel contentPanel;

    public MainFrame(User loggedUser) {
        setTitle("Car Rental Application");
        setSize(870, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top bar for navigation
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(0, 172, 237));

        // Left panel for the logo
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(Color.WHITE);
        logoPanel.setPreferredSize(new Dimension(85, 85)); // Same size as the buttons

        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("res\\R.png");
        Image scaledImage = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledImage);
        logoLabel.setIcon(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoPanel.add(logoLabel, BorderLayout.CENTER);

        topBar.add(logoPanel, BorderLayout.WEST);

        // Right panel for the buttons
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 4));
        buttonsPanel.setBackground(new Color(255, 255, 255));

        Dimension buttonSize = new Dimension(130, 85);
        JButton homeButton = new JButton("Book");
        JButton myBookingsButton = new JButton("My Bookings");
        JButton invoicesButton = new JButton("Invoices");
        JButton agreementsButton = new JButton("Agreements");
        JButton logoutButton = new JButton("Logout");

        JButton[] buttons = {homeButton, myBookingsButton, invoicesButton, agreementsButton, logoutButton};
        for (JButton button : buttons) {
            button.setBackground(new Color(0, 172, 237));
            button.setForeground(Color.WHITE);
            button.setPreferredSize(buttonSize);
            buttonsPanel.add(button);
        }

        logoutButton.setBackground(new Color(161, 1, 1));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setPreferredSize(buttonSize);

        topBar.add(buttonsPanel, BorderLayout.CENTER);

        add(topBar, BorderLayout.NORTH);

        // Content panel
        contentPanel = new JPanel(new CardLayout());
        contentPanel.add(new BrowseVehicles(this, loggedUser), "Browse Vehicles");
        contentPanel.add(new MyBookings(this, loggedUser), "My Bookings");
        contentPanel.add(new Invoices(this, loggedUser), "Invoices");
        contentPanel.add(new Agreements(this, loggedUser), "Agreements");

        add(contentPanel, BorderLayout.CENTER);

        homeButton.addActionListener(e -> switchPanel("Browse Vehicles"));
        myBookingsButton.addActionListener(e -> switchPanel("My Bookings"));
        invoicesButton.addActionListener(e -> switchPanel("Invoices"));
        agreementsButton.addActionListener(e -> switchPanel("Agreements"));
        logoutButton.addActionListener(e -> {
            dispose();
            new Login();
        });

        setVisible(true);
    }

    public void switchPanel(String panelName) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, panelName);
    }
}
