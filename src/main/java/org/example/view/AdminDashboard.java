package org.example.view;
import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("AdminDashboard");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        // Bottom panel for action buttons

        JPanel CenterPanel = new JPanel(new GridLayout(3,1,50,50));
        JButton ManageVehicleButton = new JButton("Manage vehicles");
        JButton ManageBookingsButton = new JButton("Manage Bookings");
        JButton ReportsButton = new JButton("Reports");
        CenterPanel.add(ManageVehicleButton);
        CenterPanel.add(ManageBookingsButton);
        CenterPanel.add(ReportsButton);

        JPanel bouttomPanel = new JPanel(new BorderLayout());
        JButton Logoututton = new JButton("Back to Dashboard");
        bouttomPanel.add(Logoututton);

        add(CenterPanel, BorderLayout.CENTER);
        add(bouttomPanel, BorderLayout.SOUTH);

        setVisible(true);
        this.pack();
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}