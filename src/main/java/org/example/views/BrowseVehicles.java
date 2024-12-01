package org.example.views;

import javax.swing.*;
import java.awt.*;


public class BrowseVehicles extends JFrame {

    private JButton accountBtn, browseBtn, historyBtn;
    private ImageIcon accountIcon;

    public BrowseVehicles(){
        this.setSize(700,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel slideBarPanel = new JPanel(new GridLayout(4,1));
        accountBtn = new JButton("Account");
        accountBtn.setSize(100,100);
        accountIcon = new ImageIcon("C:\\Users\\Amer_\\Downloads\\Class_DB Digram - Frame 2.jpg");

        // Resize the image
        Image scaledImage = accountIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        accountIcon = new ImageIcon(scaledImage);
        browseBtn = new JButton("Browse");
        browseBtn.setSize(100,100);

        historyBtn = new JButton("History");
        historyBtn.setSize(100,100);

        JLabel label = new JLabel("", accountIcon, JLabel.CENTER);

        label.setSize(100,100);


        slideBarPanel.add(accountBtn);
        slideBarPanel.add(browseBtn);
        slideBarPanel.add(historyBtn);
        slideBarPanel.add(label);

        mainPanel.add(slideBarPanel, BorderLayout.WEST);


        JPanel contentPanel = new JPanel(new BorderLayout());



        this.add(mainPanel);

    }


}
