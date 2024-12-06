package org.example.view;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import org.example.classes.User;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    private JPanel contentPanel;
    private Color Cyan = new Color(0, 172, 237);
    private Color Red = new Color(161, 1, 1);
    private Color White = Color.white;
    Image icon = Toolkit.getDefaultToolkit().getImage("src/main/java/org/example/res/R.png");
    public AdminDashboard(User loggedUser) {
        setTitle("Admin Dashboard");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(icon);
        setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(White);

        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(White);
        logoPanel.setPreferredSize(new Dimension(85, 85));

        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("src/main/java/org/example/res/R.png");
        Image scaledImage = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledImage);
        logoLabel.setIcon(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoPanel.add(logoLabel, BorderLayout.CENTER);

        topBar.add(logoPanel, BorderLayout.WEST);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 5));
        buttonsPanel.setBackground(White);

        Dimension buttonSize = new Dimension(130, 85);
        JButton vehiclesButton = new JButton("Manage Vehicles");
        JButton bookingsButton = new JButton("Manage Bookings");
        JButton reportsButton = new JButton("Reports");
        JButton customersButton = new JButton("Customers");
        JButton logoutButton = new JButton("Logout");

        JButton[] buttons = {vehiclesButton, bookingsButton, reportsButton, customersButton, logoutButton};
        for (JButton button : buttons) {
            button.setBackground(Cyan);
            button.setForeground(White);
            button.setPreferredSize(buttonSize);
            buttonsPanel.add(button);
        }

        logoutButton.setBackground(Red);
        logoutButton.setForeground(White);
        logoutButton.setPreferredSize(buttonSize);

        topBar.add(buttonsPanel, BorderLayout.CENTER);

        add(topBar, BorderLayout.NORTH);

        contentPanel = new JPanel(new CardLayout());
        contentPanel.add(new ManageVehicles(this), "Manage Vehicles");
        contentPanel.add(new ManageBookings(this), "Manage Bookings");
        contentPanel.add(new ReportsPanel(), "Reports");
        contentPanel.add(new CustomersInfo(this), "Customers");

        add(contentPanel, BorderLayout.CENTER);

        vehiclesButton.addActionListener(e -> switchPanel("Manage Vehicles"));
        bookingsButton.addActionListener(e -> switchPanel("Manage Bookings"));
        reportsButton.addActionListener(e -> switchPanel("Reports"));
        customersButton.addActionListener(e -> switchPanel("Customers"));

        logoutButton.addActionListener(e -> {
            dispose();
            new Login();
        });
        JButton darkModeButton = new JButton("Dark Mode");
        darkModeButton.setBackground(Color.GRAY);
        darkModeButton.setForeground(White);
        darkModeButton.setPreferredSize(buttonSize);
        buttonsPanel.add(darkModeButton);

        darkModeButton.addActionListener(e -> {
            if (UIManager.getLookAndFeel() instanceof FlatLightLaf) {
                darkModeButton.setText("Light Mode");
                darkModeButton.setForeground(Color.GRAY);
                darkModeButton.setBackground(White);
                FlatDarkLaf.setup();
            } else {
                darkModeButton.setText("Dark Mode");
                darkModeButton.setBackground(Color.GRAY);
                darkModeButton.setForeground(White);
                FlatLightLaf.setup();
            }
            SwingUtilities.updateComponentTreeUI(this);
        });

        setVisible(true);
    }


    public void switchPanel(String panelName) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, panelName);
    }
}
