package org.example.views;

import javax.swing.*;
import java.awt.*;

public class AccountPage extends JFrame {

    private JButton accountLabel, browseLabel, historyLabel;
    private JLabel contentTitle, name, email, phone, nameText, emailText, phoneText, space;
    // private ImageIcon accountIcon;

    public AccountPage(){
        this.setSize(700,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Account Page");

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int W = screenSize.width;
        int H = screenSize.height;
        this.setBounds(W/4, H/4, W/2, H/2);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel slideBarPanel = new JPanel(new GridLayout(3,1));
        
        //accountIcon = new ImageIcon("C:\\Users\\Amer_\\Downloads\\Class_DB Digram - Frame 2.jpg");
        // Resize the image
        //Image scaledImage = accountIcon.getImage().getScaledInstance(100, 100, Image.SCALE_AREA_AVERAGING);
        //accountIcon = new ImageIcon(scaledImage);

        accountLabel = new JButton("Account");
        accountLabel.setSize(100,100);

        browseLabel = new JButton("Browse");
        browseLabel.setSize(100,100);

        historyLabel = new JButton("History");
        historyLabel.setSize(100,100);




        slideBarPanel.add(accountLabel);
        slideBarPanel.add(browseLabel);
        slideBarPanel.add(historyLabel);

        
        // Second Panel
        JPanel contentpanel = new JPanel(new GridLayout(14,1));
        contentTitle = new JLabel("Account Info");

        
        name = new JLabel("Fullname:");
        nameText = new JLabel("Customer1345525, ibn Customer1345524"); // Needs to be changed using User data
        
        email = new JLabel("Email:");
        emailText = new JLabel("Customer1345525@mail.com"); // Needs to be changed using User data

        phone = new JLabel("Phone Number:");
        phoneText = new JLabel("05123456789"); // Needs to be changed using User data
        space = new JLabel(" ");

        contentpanel.add(contentTitle);
        contentpanel.add(space);
        contentpanel.add(name);
        contentpanel.add(nameText);
        contentpanel.add(email);
        contentpanel.add(emailText);
        contentpanel.add(phone);
        contentpanel.add(phoneText);

        //JPanel contentPanel = new JPanel(new BorderLayout());


        mainPanel.add(slideBarPanel, BorderLayout.WEST);
        mainPanel.add(contentpanel, BorderLayout.CENTER);


        this.add(mainPanel);
        this.setVisible(true);

    }

    public static void main(String[] args) {

        AccountPage acp = new AccountPage();

    }

}