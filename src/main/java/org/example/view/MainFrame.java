package org.example.view;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel contentPanel;

    public MainFrame() {
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
        JButton homeButton = new JButton("Home");
        JButton myBookingsButton = new JButton("My Bookings");
        JButton invoicesButton = new JButton("Invoices");
        JButton agreementsButton = new JButton("Agreements");

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

        topBar.add(homeButton);
        topBar.add(myBookingsButton);
        topBar.add(invoicesButton);
        topBar.add(agreementsButton);

        add(topBar, BorderLayout.NORTH);

        // Content panel
        contentPanel = new JPanel(new CardLayout());
        contentPanel.add(new BrowseVehicles(this), "Browse Vehicles");
        contentPanel.add(new MyBookings(this), "My Bookings");
        contentPanel.add(new Invoices(this), "Invoices");
        contentPanel.add(new Agreements(this), "Agreements");

        add(contentPanel, BorderLayout.CENTER);

        homeButton.addActionListener(e -> switchPanel("Browse Vehicles"));
        myBookingsButton.addActionListener(e -> switchPanel("My Bookings"));
        invoicesButton.addActionListener(e -> switchPanel("Invoices"));
        agreementsButton.addActionListener(e -> switchPanel("Agreements"));

        setVisible(true);
    }

    public void switchPanel(String panelName) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, panelName);
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        new MainFrame();
    }
}
