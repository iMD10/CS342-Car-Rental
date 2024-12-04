package org.example.view;

import org.example.controllers.ReportingController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportsPanel extends JPanel {

    private final ReportingController reportingController = new ReportingController();

    // Labels for dynamic updates
    private JLabel revenueLabel;
    private JLabel activeLabel;
    private JLabel completedLabel;
    private JLabel canceledLabel;
    private JLabel totalLabel;
    private JLabel customerLabel;

    public ReportsPanel() {
        setLayout(new BorderLayout());

        // Center section: Report details
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 1, 0, 10));

        centerPanel.add(createRevenuePanel());
        centerPanel.add(createBookingSummaryPanel());
        centerPanel.add(createCustomerPanel());

        // South section: Action buttons
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.add(createActionButtonsPanel(), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    private JPanel createRevenuePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        revenueLabel = new JLabel(); // Create label for dynamic updates
        revenueLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        revenueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(revenueLabel, BorderLayout.CENTER);

        // Initial update
        updateRevenuePanel();
        return panel;
    }

    private JPanel createActionButtonsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshReports(); // Refresh all report data
            }
        });
        panel.add(refreshButton, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBookingSummaryPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        Font borderFont = new Font("Arial", Font.PLAIN, 24);
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Bookings Summary");
        titledBorder.setTitleFont(borderFont);
        panel.setBorder(titledBorder);

        // Create labels for dynamic updates
        activeLabel = new JLabel();
        completedLabel = new JLabel();
        canceledLabel = new JLabel();
        totalLabel = new JLabel();

        // Set font and alignment
        activeLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        completedLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        canceledLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        totalLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        activeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        completedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        canceledLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add labels to the panel
        panel.add(activeLabel);
        panel.add(completedLabel);
        panel.add(canceledLabel);
        panel.add(totalLabel);

        // Initial update
        updateBookingSummaryPanel();
        return panel;
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        customerLabel = new JLabel(); // Create label for dynamic updates
        customerLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        customerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(customerLabel);

        // Initial update
        updateCustomerPanel();
        return panel;
    }

    // Update methods for dynamic refresh
    private void updateRevenuePanel() {
        double revenue = reportingController.getRevenue();
        revenueLabel.setText("Total Revenue: $" + revenue);
    }

    private void updateBookingSummaryPanel() {
        activeLabel.setText("Active: " + reportingController.getTotalActive());
        completedLabel.setText("Completed: " + reportingController.getTotalReturned());
        canceledLabel.setText("Canceled: " + reportingController.getTotalCancelled());
        totalLabel.setText("Total: " + reportingController.getTotalBookings());
    }

    private void updateCustomerPanel() {
        int totalCustomers = reportingController.getTotalCustomer();
        customerLabel.setText("Total Customers: " + totalCustomers);
    }

    private void refreshReports() {
        updateRevenuePanel();
        updateBookingSummaryPanel();
        updateCustomerPanel();
    }
}
