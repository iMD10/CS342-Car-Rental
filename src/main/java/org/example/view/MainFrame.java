package org.example.view;
import org.example.controllers.BookingController;
import org.example.classes.User;
import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.*;
import org.example.views.UserUIWindow;

public class MainFrame extends JFrame {

    private JPanel contentPanel;
    private Color Cyan = new Color(0, 172, 237);
    private Color Red = new Color(161, 1, 1);
    private Color White = Color.white;
    Image icon = Toolkit.getDefaultToolkit().getImage("src/main/java/org/example/res/R.png");
    public MainFrame(User loggedUser) {
        setTitle("Car Rental Application");
        setSize(1000, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(icon);
        setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Cyan);

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

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 8));
        buttonsPanel.setBackground(White);

        Dimension buttonSize = new Dimension(130, 85);
        JButton homeButton = new JButton("Book");
        JButton myBookingsButton = new JButton("My Bookings");
        JButton invoicesButton = new JButton("Invoices");
        JButton agreementsButton = new JButton("Agreements");
        JButton notificationsButton = new JButton("Notifications");
        JButton myAccountButton = new JButton("My Account");
        JButton logoutButton = new JButton("Logout");
        JButton legacyButton = new JButton("New UI");

        JButton[] buttons = {homeButton, myBookingsButton, invoicesButton, agreementsButton, notificationsButton, myAccountButton, logoutButton, legacyButton};
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
        contentPanel.add(new BrowseVehicles(this, loggedUser), "Browse Vehicles");
        contentPanel.add(new MyBookings(this, loggedUser), "My Bookings");
        contentPanel.add(new Invoices(this, loggedUser), "Invoices");
        contentPanel.add(new Agreements(this, loggedUser), "Agreements");
        contentPanel.add(new NotificationsPanel(), "Notifications");
        contentPanel.add(new MyAccountPanel(loggedUser), "My Account");

        add(contentPanel, BorderLayout.CENTER);

        homeButton.addActionListener(e -> switchPanel("Browse Vehicles"));
        myBookingsButton.addActionListener(e -> switchPanel("My Bookings"));
        invoicesButton.addActionListener(e -> switchPanel("Invoices"));
        agreementsButton.addActionListener(e -> switchPanel("Agreements"));
        notificationsButton.addActionListener(e -> switchPanel("Notifications"));
        myAccountButton.addActionListener(e -> switchPanel("My Account"));
        logoutButton.addActionListener(e -> {
            dispose();
            new Login();
        });
        NotificationsPanel.sendReminder(loggedUser);
        legacyButton.addActionListener(e -> {
            dispose();
            new UserUIWindow(loggedUser);
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

    public void addNotification(String notification) {
        for (Component comp : contentPanel.getComponents()) {
            if (comp instanceof NotificationsPanel) {
                ((NotificationsPanel) comp).addNotification(notification);
                break;
            }
        }
    }
}
