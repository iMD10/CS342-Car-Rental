package org.example.view;

import org.example.controllers.ReportingController;

import javax.swing.*;
import java.awt.*;

public class ReportsPanel extends JPanel {

    private ReportingController reportingController = new ReportingController();

    public ReportsPanel() {
        setLayout(new GridLayout(6, 1));

        JPanel revenuePanel = createRevenuePanel();
        JPanel bookingPanel = createBookingPanel();
        JPanel customerPanel = createcustomerPanel();
        JPanel ReturnedPanel = createReturnedPanel();
        JPanel ActivePanel = createActivePanel();
        JPanel eCancelledPanel = createCancelledPanel();


        add(revenuePanel);
        add(bookingPanel);
        add(customerPanel);
        add(ReturnedPanel);
        add(ActivePanel);
        add(eCancelledPanel);
    }

    private JPanel createRevenuePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        double revenue = reportingController.getRevenue();
            panel.add(new JLabel("Total Revenue:"+ revenue));
            return panel;
    }

    private JPanel createBookingPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        int TotalBookings = reportingController.getTotalBookings();
        panel.add(new JLabel("Total Bookings:"+ TotalBookings));

        return panel;
    }

    private JPanel createcustomerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        int TotalCustomer = reportingController.getTotalCustomer();
        panel.add(new JLabel("Total Customer:"+ TotalCustomer));

        return panel;
    }
    private JPanel createActivePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        int TotalActive = reportingController.getTotalActive();
        panel.add(new JLabel("Total Active:"+ TotalActive));

        return panel;
    }
    private JPanel createCancelledPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        int TotalCancelled = reportingController.getTotalCancelled();
        panel.add(new JLabel("Total Cancelled:"+ TotalCancelled));

        return panel;
    }
    private JPanel createReturnedPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        int TotalReturned = reportingController.getTotalReturned();
        panel.add(new JLabel("Total Returned:"+ TotalReturned));

        return panel;
    }
}

