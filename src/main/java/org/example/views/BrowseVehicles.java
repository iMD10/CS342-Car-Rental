package org.example.views;

import javax.swing.*;
import java.awt.*;


public class BrowseVehicles extends JFrame {

    private JLabel accountLabel, browseLabel, historyLabel;
    private ImageIcon accountIcon;

    public BrowseVehicles(){
        this.setSize(700,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel slideBarPanel = new JPanel(new GridLayout(3,1));

        accountIcon = new ImageIcon("C:\\Users\\Amer_\\Downloads\\Class_DB Digram - Frame 2.jpg");
        // Resize the image
        Image scaledImage = accountIcon.getImage().getScaledInstance(100, 100, Image.SCALE_AREA_AVERAGING);
        accountIcon = new ImageIcon(scaledImage);

        accountLabel = new JLabel("Account");
        accountLabel.setSize(100,100);

        browseLabel = new JLabel("Browse");
        browseLabel.setSize(100,100);

        historyLabel = new JLabel("History");
        historyLabel.setSize(100,100);




        slideBarPanel.add(accountLabel);
        slideBarPanel.add(browseLabel);
        slideBarPanel.add(historyLabel);

        mainPanel.add(slideBarPanel, BorderLayout.WEST);


        JPanel contentPanel = new JPanel(new BorderLayout());



        this.add(mainPanel);

    }

    public static void main(String[] args) {

        BrowseVehicles bv = new BrowseVehicles();

    }

}
