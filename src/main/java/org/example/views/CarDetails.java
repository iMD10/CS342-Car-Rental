package org.example.views;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarDetails extends JPanel {

//    private JLabel carPriceLabel, carModel;
    private JTextPane carName;
    private JLabel carImage;
    private JButton bookBtn;

    CarDetails(JPanel cardPanel, CardLayout cardLayout) {

        JPanel contentPanel = new JPanel(new BorderLayout());


        ImageIcon carImageSource = new ImageIcon("res\\sampleCar.png");
        Image scaledImage = carImageSource.getImage().getScaledInstance(350, 175, Image.SCALE_SMOOTH);

        carImageSource = new ImageIcon(scaledImage);
        carImage = new JLabel(carImageSource);

        JPanel underImagePanel = new JPanel();
        underImagePanel.setLayout(new BoxLayout(underImagePanel, BoxLayout.Y_AXIS));

        JPanel nameAndBookPanel = new JPanel();
        nameAndBookPanel.setLayout(new BoxLayout(nameAndBookPanel, BoxLayout.X_AXIS));
//        nameAndBookPanel.setMaximumSize(new Dimension(300,25));
        carName = new JTextPane();
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setBold(attributes, true);
        StyleConstants.setFontSize(attributes, 28);

        carName.setCharacterAttributes(attributes, true);
        carName.setText("Car Name");
        carName.setEditable(false);

        carName.setMaximumSize(new Dimension(200, 40));
        bookBtn = new JButton("BOOK");
        bookBtn.setBackground(Color.BLUE);
        bookBtn.setForeground(Color.WHITE);
        bookBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
        bookBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "BrowseVehicles1");
            }
        });

        JPanel btnPanel = new JPanel(new GridLayout(1,1));
        btnPanel.add(bookBtn);
        btnPanel.setMaximumSize(new Dimension(200, 40));
        btnPanel.setPreferredSize(new Dimension(200, 40));

        nameAndBookPanel.add(carName);
        nameAndBookPanel.add(Box.createHorizontalGlue());
        nameAndBookPanel.add(btnPanel);


        underImagePanel.add(nameAndBookPanel);


        underImagePanel.add(Components.createLabelRow("1,000"+ " SAR per day",true));
        underImagePanel.add(Components.createLabelRow("car model",false));
        underImagePanel.add(Components.createLabelRow("car size",false));
        underImagePanel.add(Components.createLabelRow("serial number",false));
        underImagePanel.add(Box.createVerticalGlue());

        contentPanel.add(carImage, BorderLayout.NORTH);

        JLabel paddingPanel = new JLabel();
        paddingPanel.setLayout(new BoxLayout(paddingPanel,BoxLayout.X_AXIS));
        paddingPanel.add(Box.createRigidArea(new Dimension(35, 1)));
        paddingPanel.add(underImagePanel);
        paddingPanel.add(Box.createRigidArea(new Dimension(35, 1)));

        contentPanel.add(paddingPanel, BorderLayout.CENTER);



        this.setLayout(new BorderLayout());
        this.add(contentPanel);
        this.setVisible(true);

    }



    public static void main(String[] args) {
        FlatLightLaf.setup();

//        new CarDetails();

    }

}
