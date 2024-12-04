package org.example.view;

import org.example.controllers.ReportingController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ReportsPanel extends JPanel {

    private ReportingController reportingController = new ReportingController();

    public ReportsPanel() {
        setLayout(new BorderLayout());


        // Center section: Report details
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 1, 0, 10));

        centerPanel.add(createRevenuePanel());
        centerPanel.add(createBookingSummaryPanel());
        centerPanel.add(createCustomerPanel());

        add(centerPanel, BorderLayout.CENTER);

    }

    private JPanel createRevenuePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        double revenue = reportingController.getRevenue();
        JLabel revenueLabel = new JLabel("Total Revenue: $" + revenue);
        revenueLabel.setFont(new Font("Arial",Font.PLAIN, 30));
        revenueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(revenueLabel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBookingSummaryPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBorder(BorderFactory.createTitledBorder("Bookings Summary"));
        Font borderFont = new Font("Arial", Font.PLAIN, 24);
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Bookings Summary");
        titledBorder.setTitleFont(borderFont);
        panel.setBorder(titledBorder);
        JLabel activeLabel = new JLabel("Active: " + reportingController.getTotalActive());
        activeLabel.setFont(new Font("Arial",Font.PLAIN, 30));
        JLabel completedLabel = new JLabel("Completed: " + reportingController.getTotalBookings());
        completedLabel.setFont(new Font("Arial",Font.PLAIN, 30));
        JLabel canceledLabel = new JLabel("Canceled: " + reportingController.getTotalCancelled());
        canceledLabel.setFont(new Font("Arial",Font.PLAIN, 30));
        JLabel totalLabel = new JLabel("Total: " + reportingController.getTotalBookings());
        totalLabel.setFont(new Font("Arial",Font.PLAIN, 30));


        activeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(activeLabel);
        completedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(completedLabel);
        canceledLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(canceledLabel);
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(totalLabel);

        return panel;
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        int totalCustomers = reportingController.getTotalCustomer();
        JLabel customerLabel = new JLabel("Total Customers: " + totalCustomers);
        customerLabel.setFont(new Font("Arial",Font.PLAIN, 30));
        customerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(customerLabel);
        return panel;
    }

}
