package org.example.view;

import org.example.controllers.ReportingController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportsPanel extends JPanel {

    private final ReportingController reportingController = new ReportingController();

    private JLabel revenueLabel;
    private JLabel activeLabel;
    private JLabel completedLabel;
    private JLabel canceledLabel;
    private JLabel totalLabel;
    private JLabel customerLabel;

    public ReportsPanel() {
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 1, 0, 10));

        centerPanel.add(createRevenuePanel());
        centerPanel.add(createBookingSummaryPanel());
        centerPanel.add(createCustomerPanel());

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton refreshButton = new JButton("Refresh");


        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshReports();
            }
        });

        southPanel.add(refreshButton);

        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    private JPanel createRevenuePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        revenueLabel = new JLabel();
        revenueLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        revenueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(revenueLabel, BorderLayout.CENTER);

        updateRevenuePanel();
        return panel;
    }

    private JPanel createBookingSummaryPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        Font borderFont = new Font("Arial", Font.PLAIN, 24);
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Bookings Summary");
        titledBorder.setTitleFont(borderFont);
        panel.setBorder(titledBorder);

        activeLabel = new JLabel();
        completedLabel = new JLabel();
        canceledLabel = new JLabel();
        totalLabel = new JLabel();

        activeLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        completedLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        canceledLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        totalLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        activeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        completedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        canceledLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(activeLabel);
        panel.add(completedLabel);
        panel.add(canceledLabel);
        panel.add(totalLabel);

        updateBookingSummaryPanel();
        return panel;
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        customerLabel = new JLabel();
        customerLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        customerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(customerLabel);

        updateCustomerPanel();
        return panel;
    }

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
