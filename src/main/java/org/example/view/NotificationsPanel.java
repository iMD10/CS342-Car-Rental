package org.example.view;

import javax.swing.*;
import java.awt.*;

public class NotificationsPanel extends JPanel {

    private DefaultListModel<String> notificationsModel;

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

        // Sample Notification
        addNotification("Welcome to your Notifications Panel!");
    }

    // Method to add a notification
    public void addNotification(String notification) {
        notificationsModel.addElement(notification);
    }
}
