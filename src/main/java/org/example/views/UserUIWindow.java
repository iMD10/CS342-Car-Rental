package org.example.views;

import org.example.classes.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UserUIWindow extends JFrame {

    public  static final String BROWSE_PANEL = "BrowseVehicles";
    public  static final String CAR_DETAILS_PANEL = "CarDetails";
    public  static final String CONFIRM_PANEL = "ConfirmBooking";
    public  static final String BOOKING_DONE_PANEL = "BookingDone";
    public  static final String ACCOUNT_PANEL = "AccountPage";
    public  static final String HISTORY_PANEL = "BookingHistory";
    private CardLayout cardLayout;
    private JPanel cardPanel;


    public UserUIWindow(User loggedUser){
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/java/org/example/res/R.png");
        this.setIconImage(icon);


        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int W = screenSize.width;
        int H = screenSize.height;
        // Define the new width and height
        int newWidth = (int) (W / 1.65);  // Set desired width (can adjust this value)
        int newHeight = (int) (H / 1.65); // Set desired height (can adjust this value)

        // Calculate the position to keep the window centered
        int x = (W - newWidth) / 2;
        int y = (H - newHeight) / 2;

        // Set the new bounds (position and size)
        this.setBounds(x, y, newWidth, newHeight);


        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(new BrowseVehicles(loggedUser), BROWSE_PANEL);
        cardPanel.add(new AccountPage(loggedUser), ACCOUNT_PANEL);
        cardPanel.add(new BookingHistory(loggedUser), HISTORY_PANEL);

        setLayout(new BorderLayout());

        add(createSideBarPanel(), BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);





        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);


    }
    private JPanel createSideBarPanel() {
        // Create the sidebar panel with BoxLayout
        JPanel accountSideButton, browseSideButton, historySideButton;

        JPanel sideBarPanel = new JPanel();
        sideBarPanel.setLayout(new BoxLayout(sideBarPanel, BoxLayout.Y_AXIS)); // Vertical stacking

        // Add glue at the top for automatic spacing
        sideBarPanel.add(Box.createVerticalGlue());

        // Create the sidebar buttons using the helper method
        accountSideButton = createSideBarButton("src\\main\\java\\org\\example\\res\\personIcon.png", "My Account");
        accountSideButton.addMouseListener(new MouseAction(ACCOUNT_PANEL));
        browseSideButton = createSideBarButton("src\\main\\java\\org\\example\\res\\searchIcon.png", "Browse Vehicles");
        browseSideButton.addMouseListener(new MouseAction(BROWSE_PANEL));
        historySideButton = createSideBarButton("src\\main\\java\\org\\example\\res\\bookingIcon.png", "My Bookings");
        historySideButton.addMouseListener(new MouseAction(HISTORY_PANEL));

        // Add buttons with automatic spacing
        sideBarPanel.add(accountSideButton);
        sideBarPanel.add(Box.createVerticalGlue()); // Add glue to create flexible space
        sideBarPanel.add(browseSideButton);
        sideBarPanel.add(Box.createVerticalGlue()); // Add glue to create flexible space
        sideBarPanel.add(historySideButton);

        // Add glue at the bottom for automatic spacing
        sideBarPanel.add(Box.createVerticalGlue());
        return sideBarPanel;
    }

    private static JPanel createSideBarButton(String imageIconPath, String buttonName) {
        ImageIcon icon = new ImageIcon(imageIconPath);

        JLabel iconLabel = new JLabel(icon, JLabel.CENTER);

        JLabel buttonText = new JLabel(buttonName, JLabel.CENTER);



        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.add(buttonText);
        JPanel btnPanel = new JPanel();
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        iconPanel.add(iconLabel);
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS)); // Stack components vertically

        // Add icon and text to the panel
        btnPanel.add(iconPanel);
        btnPanel.add(labelPanel);

        return btnPanel;
    }

    private class MouseAction implements MouseListener {
        private String destination;

        MouseAction(String destination) {
            this.destination = destination;

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            cardLayout.show(cardPanel, destination);
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }


}
