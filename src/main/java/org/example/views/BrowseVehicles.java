package org.example.views;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;


public class BrowseVehicles extends JFrame {

    private JPanel accountSideButton,  browseSideButton, historySideButton;
    private JLabel buttonText, searchLabel ;
    private JTextPane pageTitle;
    private JTextField searchTf;
    private ImageIcon buttonIcon;
    private JLabel accIcon;

    public BrowseVehicles(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int W = screenSize.width;
        int H = screenSize.height;
        this.setBounds(W/4, H/4, W/2, H/2);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());


        JPanel sideBarPanel = createSideBarPanel();
        // Add the slideBarPanel to the main panel
        mainPanel.add(sideBarPanel, BorderLayout.WEST);

        JPanel contentPanel = new JPanel(new BorderLayout());

        JPanel contentHead = new JPanel(new BorderLayout());

        JPanel contentHeadNorth = new JPanel();

        JPanel titleAndSearchPanel = new JPanel();
        titleAndSearchPanel.setLayout(new BoxLayout(titleAndSearchPanel,BoxLayout.X_AXIS));
        JTextPane pageTitle = new JTextPane();
            // Create styled text
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setBold(attributes, true);
        StyleConstants.setFontSize(attributes, 25);

        pageTitle.setCharacterAttributes(attributes,true);
        pageTitle.setText("Browse Vehicles");

        JLabel searchLabel = new JLabel("Search: ");
        searchLabel.setSize(100,30);

        JTextField searchTf = new JTextField(20);
        searchTf.setMinimumSize(new Dimension(100,30));
        searchTf.setMaximumSize(new Dimension(100,30));
        titleAndSearchPanel.add(pageTitle);
        titleAndSearchPanel.add(Box.createHorizontalGlue());
        titleAndSearchPanel.add(searchLabel);
        titleAndSearchPanel.add(searchTf);


//        contentHeadNorth.add(titleAndSearchPanel);
        contentHead.add(titleAndSearchPanel, BorderLayout.NORTH);
        contentPanel.add(contentHead);

        titleAndSearchPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));


        mainPanel.add(contentPanel, BorderLayout.CENTER);

        JPanel contentHeadSouth = new JPanel(new BorderLayout());




        JPanel contentList = new JPanel(new BorderLayout());





        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    /**
     * Create sideBar with buttons
     * @return JPanel object of the side bar
     */
    private JPanel createSideBarPanel() {
        // Create the sidebar panel with BoxLayout
        JPanel sideBarPanel = new JPanel();
        sideBarPanel.setLayout(new BoxLayout(sideBarPanel, BoxLayout.Y_AXIS)); // Vertical stacking

        // Add glue at the top for automatic spacing
        sideBarPanel.add(Box.createVerticalGlue());

        // Create the sidebar buttons using the helper method
         accountSideButton = createSideBarButton("C:\\Users\\Amer_\\Downloads\\Class_DB Digram - Frame 2.jpg", "Account");
         browseSideButton = createSideBarButton("C:\\Users\\Amer_\\Downloads\\Class_DB Digram - Frame 2.jpg", "Browse");
         historySideButton = createSideBarButton("C:\\Users\\Amer_\\Downloads\\Class_DB Digram - Frame 2.jpg", "History");

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

    /**
     * Create sideBar button
     *
     * @return JPanel object of the button
     */
    private JPanel createSideBarButton(String imageIconPath, String buttonName) {
        buttonIcon = new ImageIcon(imageIconPath);
        // Resize the image
        Image scaledImage = buttonIcon.getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING);
        buttonIcon = new ImageIcon(scaledImage);
        accIcon = new JLabel(buttonIcon);

        buttonText = new JLabel(buttonName);
        buttonText.setSize(100,100);

        JPanel accBtnPan = new JPanel();
        accBtnPan.setLayout(new BoxLayout(accBtnPan, BoxLayout.Y_AXIS)); // Stack components vertically

        // Add icon and text to the panel
        accBtnPan.add(accIcon);
        accBtnPan.add(buttonText);

        return accBtnPan;
    }

    public static void main(String[] args) {

        BrowseVehicles bv = new BrowseVehicles();

    }

}
