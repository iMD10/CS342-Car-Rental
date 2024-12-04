package org.example.view;

import com.formdev.flatlaf.FlatLightLaf;
import org.example.classes.User;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel contentPanel;

    public MainFrame(User loggedUser) {
        setTitle("Car Rental Application");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top bar
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topBar.setBackground(new Color(0, 172, 237));

        // Adjust the logo size and background color
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

        Dimension buttonSize = new Dimension(100, 85);
        JButton homeButton = new JButton("Book");
        JButton myBookingsButton = new JButton("My Bookings");
        JButton invoicesButton = new JButton("Invoices");
        JButton agreementsButton = new JButton("Agreements");
        JButton logoutButton = new JButton("Logout");

        homeButton.setBackground(new Color(0, 172, 237));
        homeButton.setForeground(Color.WHITE);
        homeButton.setPreferredSize(buttonSize);
        myBookingsButton.setBackground(new Color(0, 172, 237));
        myBookingsButton.setForeground(Color.WHITE);
        myBookingsButton.setPreferredSize(buttonSize);
        invoicesButton.setBackground(new Color(0, 172, 237));
        invoicesButton.setForeground(Color.WHITE);
        invoicesButton.setPreferredSize(buttonSize);
        agreementsButton.setBackground(new Color(0, 172, 237));
        agreementsButton.setForeground(Color.WHITE);
        agreementsButton.setPreferredSize(buttonSize);
        logoutButton.setBackground(new Color(161, 1, 1));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setPreferredSize(buttonSize);


        topBar.add(homeButton);
        topBar.add(myBookingsButton);
        topBar.add(invoicesButton);
        topBar.add(agreementsButton);
        topBar.add(logoutButton);

        add(topBar, BorderLayout.NORTH);

        // Content panel
        contentPanel = new JPanel(new CardLayout());
        contentPanel.add(new BrowseVehicles(this, loggedUser), "Browse Vehicles");
        contentPanel.add(new MyBookings(this, loggedUser), "My Bookings");
        contentPanel.add(new Invoices(this,loggedUser), "Invoices");
        contentPanel.add(new Agreements(this,loggedUser), "Agreements");


        add(contentPanel, BorderLayout.CENTER);

        homeButton.addActionListener(e -> switchPanel("Browse Vehicles"));
        myBookingsButton.addActionListener(e -> switchPanel("My Bookings"));
        invoicesButton.addActionListener(e -> switchPanel("Invoices"));
        agreementsButton.addActionListener(e -> switchPanel("Agreements"));
        logoutButton.addActionListener(e->{
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
