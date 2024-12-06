package org.example.view;

import org.example.classes.Booking;
import org.example.classes.User;
import org.example.controllers.BookingController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NotificationsPanel extends JPanel {

    private static DefaultListModel<String> notificationsModel;


    public NotificationsPanel() {
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Notifications");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // Notifications list
        notificationsModel = new DefaultListModel<>();
        JList<String> notificationsList = new JList<>(notificationsModel);
        notificationsList.setFont(new Font("Arial", Font.PLAIN, 16));
        notificationsList.setFixedCellHeight(50); // Set a fixed height for each notification
        JScrollPane scrollPane = new JScrollPane(notificationsList);
        add(scrollPane, BorderLayout.CENTER);

    }

    // Method to add a notification
    public static void addNotification(String notification) {
        notificationsModel.addElement(notification);
    }

    public static void sendReminder(User loggedUser) {
        BookingController bookingController = new BookingController();
        List<Booking> bookings  = bookingController.getActiveBookingsByUserid(loggedUser.getId());
        for (Booking booking : bookings) {
            notificationsModel.addElement("Reminder: You should return the vehicle of booking ID: "+ booking.getId() + " at "+ booking.getEnd_date().toLocalDateTime().toLocalDate());
        }
    }
}
