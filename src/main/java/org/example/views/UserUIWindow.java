package org.example.views;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UserUIWindow extends JFrame {

    public  static final String BROWSE_PANEL = "BrowseVehicles";
    public  static final String CAR_DETAILS_PANEL = "CarDetails";
    public  static final String ACCOUNT_PANEL = "AccountPage";
    public  static final String HISTORY_PANEL = "BookingHistory";
    private CardLayout cardLayout;
    private JPanel cardPanel;


    UserUIWindow(){


        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int W = screenSize.width;
        int H = screenSize.height;
        this.setBounds(W / 4, H / 4, W / 2, H / 2);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(new BrowseVehicles(), BROWSE_PANEL);
        cardPanel.add(new AccountPage(), ACCOUNT_PANEL);
        cardPanel.add(new BookingHistory(), HISTORY_PANEL);
//        cardPanel.add(new CarDetails(), "CarDetails");

//        JPanel mainPanel = new JPanel(new BorderLayout());      // GLOBAL panel
//        JPanel contentPanel = new BrowseVehicles();   // main content (the Right)

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
        accountSideButton = createSideBarButton("res\\personIcon.png", "Account");
        accountSideButton.addMouseListener(new MouseAction(ACCOUNT_PANEL));
        browseSideButton = createSideBarButton("res\\searchIcon.png", "Browse");
        browseSideButton.addMouseListener(new MouseAction(BROWSE_PANEL));
        historySideButton = createSideBarButton("res\\bookingIcon.png", "History");
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
        ImageIcon carImage = new ImageIcon(imageIconPath);

        JLabel accIcon = new JLabel(carImage);

        JLabel buttonText = new JLabel(buttonName);
        buttonText.setSize(100, 100);


        JPanel accBtnPan = new JPanel();
        accBtnPan.setLayout(new BoxLayout(accBtnPan, BoxLayout.Y_AXIS)); // Stack components vertically

        // Add icon and text to the panel
        accBtnPan.add(accIcon);
        accBtnPan.add(buttonText);

        return accBtnPan;
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


    public static void main(String[] args) {
        FlatLightLaf.setup();

        new UserUIWindow();
    }

}
