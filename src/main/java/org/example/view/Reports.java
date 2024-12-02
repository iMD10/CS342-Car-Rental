package org.example.view;

import javax.swing.*;
import java.awt.*;

public class Reports extends JFrame {

    private final AdminDashboard dashboard;

    public Reports(AdminDashboard dashboard) {
        this.dashboard = dashboard;
        setTitle("Reports");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Example content panel
        JPanel contentPanel = new JPanel();
        contentPanel.add(new JLabel("Reports Content Here"));

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> {
            this.setVisible(false);
            dashboard.setVisible(true);
        });
        bottomPanel.add(backButton);

        // Add panels to the frame
        add(contentPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
