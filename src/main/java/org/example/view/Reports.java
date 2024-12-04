/*package org.example.view;

import org.example.controllers.ReportingController;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Reports extends JPanel {

    private ReportingController reportsController = new ReportingController();

    public Reports() {
        setLayout(new GridLayout(3, 1));

        JPanel revenuePanel = createRevenuePanel();
        JPanel bookingPanel = createBookingPanel();
        JPanel customerPanel = createCustomerPanel();

        add(revenuePanel);
        add(bookingPanel);
        add(customerPanel);
    }

    private JPanel createRevenuePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        panel.setBorder(BorderFactory.createTitledBorder("Revenue Metrics"));

        Map<String, Double> metrics = reportsController.getRevenueMetrics();
        for (String key : metrics.keySet()) {
            panel.add(new JLabel(key + ":"));
            panel.add(new JLabel(metrics.get(key).toString()));
        }

        return panel;
    }

    private JPanel createBookingPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        panel.setBorder(BorderFactory.createTitledBorder("Booking Metrics"));

        Map<String, Integer> metrics = reportsController.getBookingMetrics();
        for (String key : metrics.keySet()) {
            panel.add(new JLabel(key + ":"));
            panel.add(new JLabel(metrics.get(key).toString()));
        }

        return panel;
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        panel.setBorder(BorderFactory.createTitledBorder("Customer Metrics"));

        Map<String, Integer> metrics = reportsController.getCustomerMetrics();
        for (String key : metrics.keySet()) {
            panel.add(new JLabel(key + ":"));
            panel.add(new JLabel(metrics.get(key).toString()));
        }

        return panel;
    }
}
*/