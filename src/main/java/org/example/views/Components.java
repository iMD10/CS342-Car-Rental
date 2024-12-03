package org.example.views;

import javax.swing.*;
import java.awt.*;

public class Components {


    /**
     * Create sideBar with buttons
     *
     * @return JPanel object of the side bar
     */
    public static JPanel createSideBarPanel() {
        // Create the sidebar panel with BoxLayout
        JPanel accountSideButton, browseSideButton, historySideButton;

        JPanel sideBarPanel = new JPanel();
        sideBarPanel.setLayout(new BoxLayout(sideBarPanel, BoxLayout.Y_AXIS)); // Vertical stacking

        // Add glue at the top for automatic spacing
        sideBarPanel.add(Box.createVerticalGlue());

        // Create the sidebar buttons using the helper method
        accountSideButton = createSideBarButton("res\\personIcon.png", "Account");
        browseSideButton = createSideBarButton("res\\searchIcon.png", "Browse");
        historySideButton = createSideBarButton("res\\bookingIcon.png", "History");

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
    private static JPanel createSideBarButton(String imageIconPath, String buttonName) {
        ImageIcon carImage = new ImageIcon(imageIconPath);
        // Resize the image
//        Image scaledImage = carImage.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
//        carImage = new ImageIcon(scaledImage);
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

    public static JPanel createLabelRow(JLabel textLabel, String text, boolean isBold) {
        JPanel labelPanel = new JPanel();
        textLabel.setText(text);
        if (isBold)
            textLabel.setFont(new Font("SansSerif", Font.BOLD, 16)); // Bold and 16-point font
        else
            textLabel.setFont(new Font("SansSerif", Font.PLAIN, 16)); // Bold and 16-point font

        labelPanel.setLayout(new BoxLayout(labelPanel,BoxLayout.X_AXIS));
        labelPanel.add(textLabel);
        labelPanel.add(Box.createHorizontalGlue());
        return labelPanel;
    }

    public static JPanel createLabelRow(String text, boolean isBold) {
        JPanel labelPanel = new JPanel();
        JLabel textLabel = new JLabel(text);

        if (isBold)
            textLabel.setFont(new Font("SansSerif", Font.BOLD, 16)); // Bold and 16-point font
        else
            textLabel.setFont(new Font("SansSerif", Font.PLAIN, 16)); // Bold and 16-point font

        labelPanel.setLayout(new BoxLayout(labelPanel,BoxLayout.X_AXIS));
        labelPanel.add(textLabel);
        labelPanel.add(Box.createHorizontalGlue());
        return labelPanel;
    }

}
