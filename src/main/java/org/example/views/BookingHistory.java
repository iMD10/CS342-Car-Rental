package org.example.views;

import org.example.classes.User;

import javax.swing.*;
import java.awt.*;

public class BookingHistory extends JPanel {


    BookingHistory(User loggedUser) {
        this.setLayout(new BorderLayout()); // Set layout for the main panel

        JLabel titleLabel;
        JPanel listPanel;


        titleLabel = new JLabel("Account Page");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 25));

        listPanel = new JPanel(new GridLayout(0, 1, 30, 20));

        for (int i = 0; i < 50; i++) {
            JPanel elementPanel = new JPanel();
            elementPanel.setLayout(new BoxLayout(elementPanel, BoxLayout.X_AXIS));

            ImageIcon carImageSource = new ImageIcon("res\\sampleCar.png");
            Image img = carImageSource.getImage(); // Transform the ImageIcon to Image
            Image scaledImg = img.getScaledInstance(120, 120 / 2, Image.SCALE_SMOOTH); // Resize the image
            carImageSource = new ImageIcon(scaledImg); // Create a new ImageIcon from the resized image
            JLabel carImage = new JLabel(carImageSource);

            Font carNameFont = new Font("SansSerif", Font.BOLD, 13);
            Font labelsFont = new Font("SansSerif", Font.PLAIN, 13);
            JLabel carName = new JLabel("Car Name");
            carName.setFont(carNameFont);
            JLabel startDate = new JLabel("From: 3/12/2024");
            startDate.setFont(labelsFont);
            JLabel endDate = new JLabel("To: 5/12/2024");
            endDate.setFont(labelsFont);
            JPanel detailsPanel = new JPanel();
            detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
            detailsPanel.add(carName);
            detailsPanel.add(startDate);
            detailsPanel.add(endDate);

            Dimension bpreferredSize = new Dimension(110, 30);  // Same size for both
            Dimension preferredSize = new Dimension(125, 30);  // Same size for both
            JButton cancelBtn = new JButton("Cancel");

            cancelBtn.setPreferredSize(bpreferredSize);
            cancelBtn.setMaximumSize(bpreferredSize);
            JLabel printAgreement = new JLabel("<html><u>Print Agreement</u></html>");
            printAgreement.setFont(new Font("SansSerif", Font.PLAIN, 15));
            printAgreement.setForeground(Color.BLUE);
            printAgreement.setPreferredSize(preferredSize);
            printAgreement.setMaximumSize(preferredSize);

            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
            buttonsPanel.add(Box.createRigidArea(new Dimension(10,10)));
            buttonsPanel.add(cancelBtn);
            buttonsPanel.add(printAgreement);
            buttonsPanel.add(Box.createRigidArea(new Dimension(10,10)));

            //            JLabel carName = new JLabel("Car Name");

            elementPanel.add(Box.createRigidArea(new Dimension(25, 1)));
            elementPanel.add(carImage);
            elementPanel.add(Box.createRigidArea(new Dimension(10, 1)));
            elementPanel.add(detailsPanel);
            elementPanel.add(Box.createHorizontalGlue());
            elementPanel.add(buttonsPanel);
            elementPanel.add(Box.createRigidArea(new Dimension(25, 1)));

            listPanel.add(elementPanel);
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        add(titleLabel, BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);



        this.setVisible(true);


    }


//    public static void main(String[] args) {
//        new BookingHistory(loggedUser);
//
//    }

}
